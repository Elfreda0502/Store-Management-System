import request from '@/utils/request'


export function listLeaving(query) {
  return request({
    url: '/store/leaving/list',
    method: 'get',
    params: query
  })
}


export function getLeaving(id) {
  return request({
    url: '/store/leaving/' + id,
    method: 'get'
  })
}


export function addLeaving(data) {
  return request({
    url: '/store/leaving',
    method: 'post',
    data: data
  })
}


export function updateLeaving(data) {
  return request({
    url: '/store/leaving',
    method: 'put',
    data: data
  })
}


export function delLeaving(id) {
  return request({
    url: '/store/leaving/' + id,
    method: 'delete'
  })
}
