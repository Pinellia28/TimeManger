const app = getApp()
const request = require('../../utils/request')

Page({
  data: {
    schedules: [],
    loading: true,
    showAddModal: false,
    showEditModal: false,
    currentSchedule: null,
    formData: {
      title: '',
      content: '',
      startTime: '',
      endTime: '',
      priority: 'normal',
      tags: '',
      groupId: null
    }
  },

  onLoad() {
    console.log('待办页面加载')
    // 检查登录状态
    const userInfo = wx.getStorageSync('userInfo')
    console.log('获取到的用户信息:', userInfo)
    if (!userInfo) {
      console.log('未登录状态，准备跳转到登录页')
      wx.showToast({
        title: '请先登录',
        icon: 'none',
        duration: 1500
      })
      setTimeout(() => {
        wx.redirectTo({
          url: '/pages/login/login'
        })
      }, 1500)
      return
    }
    console.log('用户已登录，准备加载待办事项')
    this.loadSchedules()
  },

  onShow() {
    console.log('待办页面显示')
    // 每次显示页面时检查登录状态
    const userInfo = wx.getStorageSync('userInfo')
    console.log('获取到的用户信息:', userInfo)
    if (!userInfo) {
      console.log('未登录状态，准备跳转到登录页')
      wx.showToast({
        title: '请先登录',
        icon: 'none',
        duration: 1500
      })
      setTimeout(() => {
        wx.redirectTo({
          url: '/pages/login/login'
        })
      }, 1500)
      return
    }
    console.log('用户已登录，准备加载待办事项')
    this.loadSchedules()
  },

  loadSchedules() {
    console.log('开始加载待办事项列表')
    this.setData({ loading: true })
    request.get('/schedules')
      .then(res => {
        console.log('获取待办事项响应:', res)
        if (res.code === 200) {
          // 格式化日期显示
          const formattedSchedules = res.data.map(schedule => ({
            ...schedule,
            startTime: this.formatDate(schedule.startTime),
            endTime: this.formatDate(schedule.endTime)
          }))
          console.log('格式化后的待办事项:', formattedSchedules)
          this.setData({
            schedules: formattedSchedules,
            loading: false
          })
        } else if (res.code === 401) {
          // 未登录或登录已过期
          console.log('会话已过期，准备重新登录')
          wx.removeStorageSync('userInfo')
          wx.showToast({
            title: '登录已过期，请重新登录',
            icon: 'none',
            duration: 1500
          })
          setTimeout(() => {
            wx.redirectTo({
              url: '/pages/login/login'
            })
          }, 1500)
        } else {
          console.log('加载待办事项失败:', res.message)
          wx.showToast({
            title: res.message || '加载失败',
            icon: 'error'
          })
        }
      })
      .catch(err => {
        console.error('加载待办事项错误:', err)
        wx.showToast({
          title: '网络错误',
          icon: 'error'
        })
      })
      .finally(() => {
        this.setData({ loading: false })
      })
  },

  formatDate(dateString) {
    if (!dateString) return ''
    const date = new Date(dateString)
    const year = date.getFullYear()
    const month = String(date.getMonth() + 1).padStart(2, '0')
    const day = String(date.getDate()).padStart(2, '0')
    return `${year}-${month}-${day}`
  },

  showAddModal() {
    // 检查登录状态
    const userInfo = wx.getStorageSync('userInfo')
    if (!userInfo) {
      wx.showToast({
        title: '请先登录',
        icon: 'none',
        duration: 1500
      })
      setTimeout(() => {
        wx.redirectTo({
          url: '/pages/login/login'
        })
      }, 1500)
      return
    }

    this.setData({
      showAddModal: true,
      formData: {
        title: '',
        content: '',
        startTime: '',
        endTime: '',
        priority: 'normal',
        tags: '',
        groupId: null
      }
    })
  },

  hideAddModal() {
    this.setData({ showAddModal: false })
  },

  showEditModal(e) {
    // 检查登录状态
    const userInfo = wx.getStorageSync('userInfo')
    if (!userInfo) {
      wx.showToast({
        title: '请先登录',
        icon: 'none',
        duration: 1500
      })
      setTimeout(() => {
        wx.redirectTo({
          url: '/pages/login/login'
        })
      }, 1500)
      return
    }

    const schedule = e.currentTarget.dataset.schedule
    this.setData({
      showEditModal: true,
      currentSchedule: schedule,
      formData: {
        title: schedule.title,
        content: schedule.content,
        startTime: this.formatDate(schedule.startTime),
        endTime: this.formatDate(schedule.endTime),
        priority: schedule.priority,
        tags: schedule.tags,
        groupId: schedule.groupId
      }
    })
  },

  hideEditModal() {
    this.setData({ showEditModal: false })
  },

  handleInput(e) {
    const { field } = e.currentTarget.dataset
    this.setData({
      [`formData.${field}`]: e.detail.value
    })
  },

  handlePriorityChange(e) {
    const priorityMap = {
      0: 'high',
      1: 'medium',
      2: 'low'
    }
    this.setData({
      'formData.priority': priorityMap[e.detail.value]
    })
  },

  handleSubmit() {
    // 检查登录状态
    const userInfo = wx.getStorageSync('userInfo')
    if (!userInfo) {
      wx.showToast({
        title: '请先登录',
        icon: 'none',
        duration: 1500
      })
      setTimeout(() => {
        wx.redirectTo({
          url: '/pages/login/login'
        })
      }, 1500)
      return
    }

    const { formData } = this.data
    if (!formData.title) {
      wx.showToast({
        title: '标题不能为空',
        icon: 'error'
      })
      return
    }

    // 格式化日期为ISO格式
    const formattedData = {
      ...formData,
      startTime: formData.startTime ? new Date(formData.startTime).toISOString() : null,
      endTime: formData.endTime ? new Date(formData.endTime).toISOString() : null
    }

    console.log('提交的待办事项数据:', formattedData)

    const url = this.data.showEditModal ? 
      `/schedules/${this.data.currentSchedule.id}` : 
      '/schedules'
    const method = this.data.showEditModal ? 'PUT' : 'POST'

    request[method.toLowerCase()](url, formattedData)
      .then(res => {
        console.log('保存待办事项响应:', res)
        if (res.code === 200) {
          wx.showToast({
            title: '保存成功',
            icon: 'success'
          })
          this.hideAddModal()
          this.hideEditModal()
          this.loadSchedules()
        } else if (res.code === 401) {
          // 未登录或登录已过期
          console.log('会话已过期，准备重新登录')
          wx.removeStorageSync('userInfo')
          wx.showToast({
            title: '登录已过期，请重新登录',
            icon: 'none',
            duration: 1500
          })
          setTimeout(() => {
            wx.redirectTo({
              url: '/pages/login/login'
            })
          }, 1500)
        } else {
          console.log('保存待办事项失败:', res.message)
          wx.showToast({
            title: res.message || '保存失败',
            icon: 'error'
          })
        }
      })
      .catch(err => {
        console.error('保存待办事项错误:', err)
        wx.showToast({
          title: '网络错误',
          icon: 'error'
        })
      })
  },

  deleteSchedule(e) {
    // 检查登录状态
    const userInfo = wx.getStorageSync('userInfo')
    if (!userInfo) {
      wx.showToast({
        title: '请先登录',
        icon: 'none',
        duration: 1500
      })
      setTimeout(() => {
        wx.redirectTo({
          url: '/pages/login/login'
        })
      }, 1500)
      return
    }

    const { id } = e.currentTarget.dataset
    wx.showModal({
      title: '确认删除',
      content: '确定要删除这个待办事项吗？',
      success: (res) => {
        if (res.confirm) {
          console.log('准备删除待办事项:', id)
          request.delete(`/schedules/${id}`)
            .then(res => {
              console.log('删除待办事项响应:', res)
              if (res.code === 200) {
                wx.showToast({
                  title: '删除成功',
                  icon: 'success'
                })
                this.loadSchedules()
              } else if (res.code === 401) {
                // 未登录或登录已过期
                console.log('会话已过期，准备重新登录')
                wx.removeStorageSync('userInfo')
                wx.showToast({
                  title: '登录已过期，请重新登录',
                  icon: 'none',
                  duration: 1500
                })
                setTimeout(() => {
                  wx.redirectTo({
                    url: '/pages/login/login'
                  })
                }, 1500)
              } else {
                console.log('删除待办事项失败:', res.message)
                wx.showToast({
                  title: res.message || '删除失败',
                  icon: 'error'
                })
              }
            })
            .catch(err => {
              console.error('删除待办事项错误:', err)
              wx.showToast({
                title: '网络错误',
                icon: 'error'
              })
            })
        }
      }
    })
  }
}) 