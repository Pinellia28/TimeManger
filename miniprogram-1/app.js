// app.js
App({
  globalData: {
    userInfo: null,
    apiBaseUrl: 'http://localhost:8080/api'
  },
  
  onLaunch() {
    // Check if user is logged in
    const userInfo = wx.getStorageSync('userInfo')
    if (userInfo) {
      this.globalData.userInfo = userInfo
      
      // Verify with server
      this.checkSession()
    }
  },
  
  // Check if session is valid
  checkSession() {
    const that = this
    wx.request({
      url: this.globalData.apiBaseUrl + '/user/current',
      method: 'GET',
      success(res) {
        if (res.data.code !== 200) {
          // Session invalid, clear storage
          that.clearUserInfo()
        }
      },
      fail() {
        // Network error, don't clear
      }
    })
  },
  
  // Clear user info from storage
  clearUserInfo() {
    this.globalData.userInfo = null
    wx.removeStorageSync('userInfo')
  }
})
