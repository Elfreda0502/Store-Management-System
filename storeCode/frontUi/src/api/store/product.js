import request from '@/utils/request'


export function index(query) {
  return request({
    url: '/store/product/index',
    method: 'get',
    params: query
  })
}

export function listProduct(query) {
  return request({
    url: '/store/product/list',
    method: 'get',
    params: query
  })
}


export function getProduct(id) {
  return request({
    url: '/store/product/' + id,
    method: 'get'
  })
}


export function addProduct(data) {
  return request({
    url: '/store/product',
    method: 'post',
    data: data
  })
}


export function updateProduct(data) {
  return request({
    url: '/store/product',
    method: 'put',
    data: data
  })
}


export function delProduct(id) {
  return request({
    url: '/store/product/' + id,
    method: 'delete'
  })
}
