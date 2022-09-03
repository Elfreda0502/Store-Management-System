import request from '@/utils/request'


export function listCart(query) {
  return request({
    url: '/store/cart/list',
    method: 'get',
    params: query
  })
}


export function updateCartNum(query) {
  return request({
    url: '/store/cart/updateCartNum',
    method: 'get',
    params: query
  })
}











export function userListCart(query) {
  return request({
    url: '/store/cart/userList',
    method: 'get',
    params: query
  })
}


export function getCart(id) {
  return request({
    url: '/store/cart/' + id,
    method: 'get'
  })
}


export function addCart(data) {
  return request({
    url: '/store/cart',
    method: 'post',
    data: data
  })
}


export function updateCart(data) {
  return request({
    url: '/store/cart',
    method: 'put',
    data: data
  })
}


export function delCart(id) {
  return request({
    url: '/store/cart/' + id,
    method: 'delete'
  })
}
