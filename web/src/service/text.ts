import request from '@/utils/request/index'

interface Req {
  name: string
}
interface Res {
  code: number
  msg: string
  data: any
}
export const getTextData = (data: Req) => {
  return request<Req, Res>({
    url: '/user/info',
    method: 'GET',
    data,
  })
}