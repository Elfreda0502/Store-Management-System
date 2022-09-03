import request from '@/utils/request'


export function listAddress(query) {
  return request({
    url: '/user/address/list',
    method: 'get',
    params: query
  })
}


export function getAddress(id) {
  return request({
    url: '/user/address/' + id,
    method: 'get'
  })
}


export function addAddress(data) {
  return request({
    url: '/user/address',
    method: 'post',
    data: data
  })
}


export function updateAddress(data) {
  return request({
    url: '/user/address',
    method: 'put',
    data: data
  })
}


export function delAddress(id) {
  return request({
    url: '/user/address/' + id,
    method: 'delete'
  })
}
