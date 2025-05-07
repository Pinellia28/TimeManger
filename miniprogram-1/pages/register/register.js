// pages/register/register.js
/**
 * 注册页面
 * 
 * 该页面处理用户注册相关的逻辑，包括：
 * 1. 处理用户注册表单的输入（用户名、密码、昵称）
 * 2. 验证表单数据
 * 3. 提交注册请求并处理响应
 * 4. 注册成功后跳转到登录页面
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
    nickname: '', // 昵称
    errorMsg: ''  // 错误信息
  },

  /**
   * 用户名输入事件处理函数
   * 
   * @param {Object} e 输入事件对象，包含用户输入的值
   */
  usernameInput: function (e) {
    this.setData({
      username: e.detail.value
    })
  },

  /**
   * 密码输入事件处理函数
   * 
   * @param {Object} e 输入事件对象，包含用户输入的值
   */
  passwordInput: function (e) {
    this.setData({
      password: e.detail.value
    })
  },

  /**
   * 昵称输入事件处理函数
   * 
   * @param {Object} e 输入事件对象，包含用户输入的值
   */
  nicknameInput: function (e) {
    this.setData({
      nickname: e.detail.value
    })
  },

  /**
   * 注册表单提交处理函数
   * 
   * 验证表单数据，发送注册请求，处理注册结果
   */
  registerSubmit: function () {
    const that = this
    const { username, password, nickname } = this.data

    // 表单验证 - 检查必填字段
    if (!username || !password) {
      this.setData({
        errorMsg: '用户名和密码不能为空'
      })
      return
    }

    // 验证用户名长度
    if (username.length < 4 || username.length > 20) {
      this.setData({
        errorMsg: '用户名长度需要在4-20个字符之间'
      })
      return
    }

    // 验证密码长度
    if (password.length < 6) {
      this.setData({
        errorMsg: '密码长度至少为6个字符'
      })
      return
    }

    // 清除之前的错误信息
    this.setData({
      errorMsg: ''
    })

    // 显示加载提示
    wx.showLoading({
      title: '注册中...',
    })

    // 调用注册API
    request.post('/user/register', { username, password, nickname })
      .then(res => {
        // 隐藏加载提示
        wx.hideLoading()
        
        if (res.code === 200) {
          // 注册成功，显示成功提示
          wx.showToast({
            title: '注册成功',
            icon: 'success',
            duration: 1500
          })

          // 延迟跳转到登录页面，让用户看到成功提示
          setTimeout(function () {
            wx.navigateTo({
              url: '/pages/login/login',
            })
          }, 1500)
        } else {
          // 注册失败，显示错误信息
          that.setData({
            errorMsg: res.message || '注册失败，请重试'
          })
        }
      })
      .catch(err => {
        // 网络请求异常处理
        wx.hideLoading()
        that.setData({
          errorMsg: '网络错误，请检查网络连接'
        })
        console.error(err)
      })
  },

  /**
   * 跳转到登录页面
   */
  goToLogin: function () {
    wx.navigateTo({
      url: '/pages/login/login',
    })
  }
}) 