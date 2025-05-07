// pages/login/login.js
/**
 * 登录页面
 * 
 * 该页面处理用户登录相关的逻辑，包括：
 * 1. 检查用户是否已登录，若已登录则直接跳转到首页
 * 2. 处理用户名和密码输入
 * 3. 提交登录表单并处理请求响应
 * 4. 处理登录成功后的跳转
 */

// 获取全局应用实例
const app = getApp()
// 导入请求工具函数
const request = require('../../utils/request')

Page({
  /**
   * 页面初始数据
   */
  data: {
    username: '', // 用户名
    password: '', // 密码
    loading: false
  },

  /**
   * 生命周期函数--监听页面加载
   * 
   * 检查用户是否已登录，若已登录则跳转至首页
   * @param {Object} options 页面跳转参数
   */
  onLoad: function (options) {
    // 检查是否已登录
    const userInfo = wx.getStorageSync('userInfo')
    if (userInfo) {
      console.log('用户已登录，正在跳转到首页', userInfo)
      wx.switchTab({
        url: '/pages/index/index',
        success: () => {
          // 刷新首页数据
          const pages = getCurrentPages()
          const indexPage = pages[pages.length - 1]
          if (indexPage && indexPage.loadUserInfo) {
            indexPage.loadUserInfo()
          }
        }
      })
    }
  },

  /**
   * 用户名输入事件处理函数
   * 
   * @param {Object} e 输入事件对象，包含用户输入的值
   */
  handleInput: function (e) {
    const { field } = e.currentTarget.dataset
    this.setData({
      [field]: e.detail.value
    })
  },

  /**
   * 登录表单提交处理函数
   * 
   * 验证表单数据，发送登录请求，处理登录结果
   */
  loginSubmit: function () {
    const { username, password } = this.data
    if (!username || !password) {
      wx.showToast({
        title: '请填写完整信息',
        icon: 'none'
      })
      return
    }

    this.setData({ loading: true })
    console.log('正在尝试登录，用户名:', username)

    request.post('/user/login', {
      username,
      password
    })
      .then(res => {
        console.log('登录响应:', res)
        if (res.code === 200) {
          // 保存用户信息到本地存储
          wx.setStorageSync('userInfo', res.data)
          console.log('登录成功，保存用户信息:', res.data)
          
          wx.showToast({
            title: '登录成功',
            icon: 'success',
            duration: 1500
          })

          // 延迟跳转，让用户看到成功提示
          setTimeout(() => {
            wx.switchTab({
              url: '/pages/index/index',
              success: () => {
                // 刷新首页数据
                const pages = getCurrentPages()
                const indexPage = pages[pages.length - 1]
                if (indexPage && indexPage.loadUserInfo) {
                  indexPage.loadUserInfo()
                }
              }
            })
          }, 1500)
        } else {
          console.log('登录失败:', res.message)
          wx.showToast({
            title: res.message || '登录失败',
            icon: 'error'
          })
        }
      })
      .catch(err => {
        console.error('登录错误:', err)
        wx.showToast({
          title: '网络错误',
          icon: 'error'
        })
      })
      .finally(() => {
        this.setData({ loading: false })
      })
  },

  /**
   * 跳转到注册页面
   */
  navigateToRegister: function () {
    wx.navigateTo({
      url: '/pages/register/register'
    })
  }
}) 