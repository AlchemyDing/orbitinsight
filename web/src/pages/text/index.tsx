import {getTextData} from '@/service/text'
import { Button } from 'antd'
import { useEffect } from 'react'

const Text=()=>{
  const getTextDataFun=async()=>{
    const data = await getTextData({name:'text'})
    console.log('获取的数据：',data)
  }
  useEffect(()=>{
    localStorage.setItem('token','123456')
  },[])
  return(
    <div>
      <Button onClick={()=>getTextDataFun()}>请求数据</Button>
    </div>
  )
}

export default Text