import request from '@/utils/request'


export function listPurchase(query) {
  return request({
    url: '/store/purchase/list',
    method: 'get',
    params: query
  })
}


export function getPurchase(id) {
  return request({
    url: '/store/purchase/' + id,
    method: 'get'
  })
}


export function addPurchase(data) {
  return request({
    url: '/store/purchase',
    method: 'post',
    data: data
  })
}


export function updatePurchase(data) {
  return request({
    url: '/store/purchase',
    method: 'put',
    data: data
  })
}


export function delPurchase(id) {
  return request({
    url: '/store/purchase/' + id,
    method: 'delete'
  })
}
