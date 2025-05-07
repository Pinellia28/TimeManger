// index.js
/**
 * 首页
 * 
 * 该页面是应用的主页面，负责：
 * 1. 检查用户登录状态，未登录则跳转到登录页
 * 2. 显示用户信息
 * 3. 处理用户登出操作
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
    userInfo: null, // 用户信息
    loading: true   // 加载状态
  },

  /**
   * 生命周期函数--监听页面加载
   * 
   * 页面创建时执行，调用检查登录状态函数
   */
  onLoad() {
    this.checkLoginStatus()
  },

  /**
   * 生命周期函数--监听页面显示
   * 
   * 页面显示/切换回前台时执行，调用检查登录状态函数
   * 确保页面每次可见时都验证用户的登录状态
   */
  onShow() {
    this.checkLoginStatus()
  },

  /**
   * 检查用户登录状态
   * 
   * 从本地存储获取用户信息，判断是否已登录
   * 已登录则更新页面数据，未登录则跳转到登录页
   */
  checkLoginStatus() {
    const userInfo = wx.getStorageSync('userInfo')
    if (userInfo) {
      // 已登录，更新页面数据
      this.setData({
        userInfo: userInfo,
        loading: false
      })
      
      // 显示登录成功的欢迎消息
      wx.showToast({
        title: '登录成功',
        icon: 'success',
        duration: 2000
      })
    } else {
      // 未登录，重定向到登录页
      wx.redirectTo({
        url: '/pages/login/login'
      })
    }
  },

  /**
   * 用户登出
   * 
   * 弹出确认对话框，确认后调用登出API
   * 清除本地存储的用户信息，并跳转到登录页
   */
  logout() {
    const that = this
    // 显示确认对话框
    wx.showModal({
      title: '提示',
      content: '确定要退出登录吗？',
      success(res) {
        if (res.confirm) {
          // 用户点击确定，显示加载提示
          wx.showLoading({
            title: '退出中...'
          })

          // 调用登出API
          request.post('/user/logout')
            .then(() => {
              // 登出成功，移除本地存储的用户信息
              wx.removeStorageSync('userInfo')
              
              // 跳转到登录页
              wx.redirectTo({
                url: '/pages/login/login'
              })
            })
            .catch(() => {
              // 即使API调用失败，也清除本地存储并跳转
              // 确保用户可以退出当前会话
              wx.removeStorageSync('userInfo')
              wx.redirectTo({
                url: '/pages/login/login'
              })
            })
            .finally(() => {
              // 无论成功失败，都隐藏加载提示
              wx.hideLoading()
            })
        }
        // 如果用户点击取消，不执行任何操作
      }
    })
  },

  navigateToSchedule() {
    wx.navigateTo({
      url: '/pages/schedule/schedule'
    })
  }
})
