import request from '@/utils/request'


export function listCategory(query) {
  return request({
    url: '/store/category/list',
    method: 'get',
    params: query
  })
}


export function getCategory(id) {
  return request({
    url: '/store/category/' + id,
    method: 'get'
  })
}


export function addCategory(data) {
  return request({
    url: '/store/category',
    method: 'post',
    data: data
  })
}


export function updateCategory(data) {
  return request({
    url: '/store/category',
    method: 'put',
    data: data
  })
}


export function delCategory(id) {
  return request({
    url: '/store/category/' + id,
    method: 'delete'
  })
}
