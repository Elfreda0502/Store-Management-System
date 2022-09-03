import request from '@/utils/request'


export function listOrder(query) {
  return request({
    url: '/store/order/list',
    method: 'get',
    params: query
  })
}


export function myOrderList(query) {
  return request({
    url: '/store/order/myOrderList',
    method: 'get',
    params: query
  })
}


export function getOrder(id) {
  return request({
    url: '/store/order/' + id,
    method: 'get'
  })
}


export function addOrder(data) {
  return request({
    url: '/store/order',
    method: 'post',
    data: data
  })
}


export function orderRefund(data) {
  return request({
    url: '/store/order/refund',
    method: 'get',
    params: data
  })
}


export function orderRefundFail(data) {
  return request({
    url: '/store/order/refundFail',
    method: 'get',
    params: data
  })
}





//deliverGoods

export function deliverGoods(data) {
  return request({
    url: '/store/order/deliverGoods',
    method: 'post',
    data: data
  })
}


export function requestRefund(data) {
  return request({
    url: '/store/order/requestRefund',
    method: 'post',
    data: data
  })
}


export function createOrder(data) {
  return request({
    url: '/store/order/createOrder',
    method: 'post',
    data: data
  })
}


export function updateOrder(data) {
  return request({
    url: '/store/order',
    method: 'put',
    data: data
  })
}


export function delOrder(id) {
  return request({
    url: '/store/order/' + id,
    method: 'delete'
  })
}
