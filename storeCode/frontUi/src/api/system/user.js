import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/store";


export function listUser(query) {
  return request({
    url: '/system/user/list',
    method: 'get',
    params: query
  })
}


export function getUser(userId) {
  return request({
    url: '/system/user/' + parseStrEmpty(userId),
    method: 'get'
  })
}


export function addUser(data) {
  return request({
    url: '/system/user',
    method: 'post',
    data: data
  })
}


export function updateUser(data) {
  return request({
    url: '/system/user',
    method: 'put',
    data: data
  })
}


export function delUser(userId) {
  return request({
    url: '/system/user/' + userId,
    method: 'delete'
  })
}


export function resetUserPwd(userId, password) {
  const data = {
    userId,
    password
  }
  return request({
    url: '/system/user/resetPwd',
    method: 'put',
    data: data
  })
}


export function changeUserStatus(userId, status) {
  const data = {
    userId,
    status
  }
  return request({
    url: '/system/user/changeStatus',
    method: 'put',
    data: data
  })
}


export function getUserProfile() {
  return request({
    url: '/system/user/profile',
    method: 'get'
  })
}


export function updateUserProfile(data) {
  return request({
    url: '/system/user/profile',
    method: 'put',
    data: data
  })
}


export function updateUserPwd(oldPassword, newPassword) {
  const data = {
    oldPassword,
    newPassword
  }
  return request({
    url: '/system/user/profile/updatePwd',
    method: 'put',
    params: data
  })
}


export function uploadAvatar(data) {
  return request({
    url: '/system/user/profile/avatar',
    method: 'post',
    data: data
  })
}


export function getAuthRole(userId) {
  return request({
    url: '/system/user/authRole/' + userId,
    method: 'get'
  })
}


export function updateAuthRole(data) {
  return request({
    url: '/system/user/authRole',
    method: 'put',
    params: data
  })
}
