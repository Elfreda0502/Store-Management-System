
import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

const routes = [

  {
    path: '/',
    name: 'Home',
    component: () => import('../views/Home.vue')
  },
  {
    path: '/error',
    name: 'Error',
    component: () => import('../components/Error.vue')
  },
  {
    path: '/goods',
    name: 'Goods',
    component: () => import('../views/Goods.vue')
  },
  {
    path: '/leaving',
    name: 'Leaving',
    component: () => import('../views/Leaving.vue')
  },
  {
    path: '/goods/details',
    name: 'Details',
    component: () => import('../views/Details.vue')
  },
  {
    path: '/shoppingCart',
    name: 'ShoppingCart',
    component: () => import('../views/ShoppingCart.vue'),
    meta: {
      requireAuth: true
    }
  },
  {
    path: '/collect',
    name: 'Collect',
    component: () => import('../views/Collect.vue'),
    meta: {
      requireAuth: true
    }
  },
  {
    path: '/order',
    name: 'Order',
    component: () => import('../views/Order.vue'),
    meta: {
      requireAuth: true
    }
  },
  // {
  //   path: '/storehouse',
  //   name: 'Storehouse',
  //   component: () => import('../views/Storehouse.vue'),
  //   meta: {
  //     requireAuth: true
  //   }
  // },
  {
    path: '/confirmOrder',
    name: 'ConfirmOrder',
    component: () => import('../views/ConfirmOrder.vue'),
    meta: {
      requireAuth: true
    }
  }
]

const router = new Router({


  routes
})

/* Since Vue-router changed the $router.push() method to Promise after 3.1. So if there is no callback function, the error message will be handed over to the global routing error handling.
vue-router first reported an Uncaught (in promise) error (because the push did not add a callback), and then the NavigationDuplicated error was triggered when the route was clicked (the error occurred in the route, the global error handling was printed out). */

const originalPush = Router.prototype.push;
Router.prototype.push = function push (location, onResolve, onReject) {
  if (onResolve || onReject)
    return originalPush.call(this, location, onResolve, onReject)
  return originalPush.call(this, location).catch(err => err)
}

export default router
