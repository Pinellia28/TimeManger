// utils/request.js
/**
 * HTTP请求工具模块
 * 
 * 该模块封装了微信小程序的网络请求接口，提供了更简便的API调用方式
 * 统一处理请求错误和响应格式，简化页面中的网络请求代码
 */

// API服务器基础URL
const BASE_URL = 'http://localhost:8080/api';

/**
 * 发送HTTP请求的通用函数
 * 
 * @param {string} url - 请求路径（不包含基础URL）
 * @param {string} method - 请求方法（GET, POST等）
 * @param {Object} data - 请求参数
 * @param {Object} header - 请求头
 * @returns {Promise} 返回Promise对象，成功时解析为响应数据，失败时拒绝并返回错误信息
 */
function request(url, method, data, header = {}) {
  return new Promise((resolve, reject) => {
    console.log(`发起请求: ${method} ${BASE_URL}${url}`, data)
    
    // 获取用户信息
    const userInfo = wx.getStorageSync('userInfo')
    
    // 设置请求头
    const headers = {
      'content-type': 'application/json',  // 默认以JSON格式发送数据
      ...header // 合并自定义请求头
    }
    
    // 如果用户已登录，添加用户ID到请求头
    if (userInfo && userInfo.id) {
      headers['X-User-ID'] = userInfo.id
    }
    
    wx.request({
      url: BASE_URL + url,  // 完整的请求URL
      method: method,       // 请求方法
      data: data,           // 请求数据
      header: headers,
      withCredentials: true, // 允许跨域请求时携带Cookie
      success: (res) => {
        console.log(`请求响应: ${method} ${BASE_URL}${url}`, res)
        
        // 请求成功，HTTP状态码为200
        if (res.statusCode === 200) {
          resolve(res.data);  // 返回服务器响应数据
        } else if (res.statusCode === 401) {
          // 未登录或登录已过期
          console.log('登录已过期', res)
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
          reject({
            code: 401,
            message: '登录已过期',
            response: res
          })
        } else {
          // HTTP请求成功但状态码非200，拒绝Promise并返回错误信息
          console.log('请求失败', res)
          reject({
            message: '请求失败',
            ...res.data,
            response: res
          });
        }
      },
      fail: (err) => {
        console.log(`请求异常: ${method} ${BASE_URL}${url}`, err)
        
        // 处理开发环境跨域问题的提示
        if (err.errMsg && err.errMsg.indexOf('url not in domain list') > -1) {
          wx.showModal({
            title: '开发环境提示',
            content: '请在微信开发者工具中勾选"不校验合法域名"选项',
            showCancel: false
          });
        }
        // 网络请求失败，拒绝Promise并返回错误信息
        reject(err);
      }
    });
  });
}

/**
 * 发送GET请求
 * 
 * @param {string} url - 请求路径（不包含基础URL）
 * @param {Object} params - 请求参数
 * @returns {Promise} 返回Promise对象
 */
function get(url, params = {}) {
  return request(url, 'GET', params);
}

/**
 * 发送POST请求
 * 
 * @param {string} url - 请求路径（不包含基础URL）
 * @param {Object} data - 请求数据
 * @returns {Promise} 返回Promise对象
 */
function post(url, data = {}) {
  return request(url, 'POST', data);
}

/**
 * 发送PUT请求
 * 
 * @param {string} url - 请求路径（不包含基础URL）
 * @param {Object} data - 请求数据
 * @returns {Promise} 返回Promise对象
 */
function put(url, data = {}) {
  return request(url, 'PUT', data);
}

/**
 * 发送DELETE请求
 * 
 * @param {string} url - 请求路径（不包含基础URL）
 * @param {Object} data - 请求数据
 * @returns {Promise} 返回Promise对象
 */
function del(url, data = {}) {
  return request(url, 'DELETE', data);
}

// 导出模块接口
module.exports = {
  get,
  post,
  put,
  delete: del,
  request
}; 