import Request from './request'
import { AxiosResponse } from 'axios'

import type { RequestConfig } from '@/utils/request/request'
import { message } from 'antd'

//TODO 待后端确定返回类型
export interface TextResponse<T> {
  code: string
  desc: string
  result: T
}

// 重写返回类型
interface RequestConfigType<T, R> extends RequestConfig<TextResponse<R>> {
  data?: T
}
const request = new Request({
  baseURL: process.env.APP_API_URL_ZH,
  timeout: 1000 * 60 * 5,
  interceptors: {
    // 请求拦截器
    requestInterceptors: (config) => {
      const token = localStorage.getItem('token')
      if (token) {
        config.headers.Authorization = `${token}`
      }
      return config
    },
    // 响应拦截器
    responseInterceptors:(result:AxiosResponse)=>{
      console.log('接口响应拦截',result.data)
      const {code} = result.data
      switch(code){
        case 'E00050':
          message.error('登录失效，请重新登录')
          break
      }
      return result
    }
  },
})

/**
 * @description: 函数的描述
 * @generic D 请求参数
 * @generic T 响应结构
 * @param {RequestConfigType} config 不管是GET还是POST请求都使用data
 * @returns {Promise}
 */
const myRequest = <D = any, T = any>(config: RequestConfigType<D, T>) => {
  const { method = 'GET' } = config
  if (method === 'get' || method === 'GET') {
    config.params = config.data
  }
  return request.request<TextResponse<T>>(config)
}
// // 取消请求
// export const cancelRequest = (url: string | string[]) => {
//   return request.cancelRequest(url)
// }
// // 取消全部请求
// export const cancelAllRequest = () => {
//   return request.cancelAllRequest()
// }

export default myRequest