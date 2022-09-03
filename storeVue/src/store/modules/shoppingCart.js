
export default {
  state: {
    shoppingCart: []
   
  },
  getters: {
    getShoppingCart (state) {
      
      
      return state.shoppingCart;
    },
    getNum (state) {
      
      let totalNum = 0;
      for (let i = 0; i < state.shoppingCart.length; i++) {
        const temp = state.shoppingCart[i];
        totalNum += temp.cartNum;
      }
      return totalNum;
    },
    getIsAllCheck (state) {
      
      let isAllCheck = true;
      for (let i = 0; i < state.shoppingCart.length; i++) {
        const temp = state.shoppingCart[i];
        
        if (!temp.check) {
          isAllCheck = false;
          return isAllCheck;
        }
      }
      return isAllCheck;
    },
    getCheckGoods (state) {
     
      let checkGoods = [];
      for (let i = 0; i < state.shoppingCart.length; i++) {
        const temp = state.shoppingCart[i];
        if (temp.check) {
          checkGoods.push(temp);
        }
      }
      return checkGoods;
    },
    getCheckNum (state) {
      
      let totalNum = 0;
      for (let i = 0; i < state.shoppingCart.length; i++) {
        const temp = state.shoppingCart[i];
        if (temp.check) {
          totalNum += temp.cartNum;
        }
      }
      return totalNum;
    },
    getTotalPrice (state) {
     
      let totalPrice = 0;
      for (let i = 0; i < state.shoppingCart.length; i++) {
        const temp = state.shoppingCart[i];
        if (temp.check) {
          totalPrice += temp.price * temp.cartNum;
        }
      }
      console.log(totalPrice.toFixed(2))
      return totalPrice.toFixed(2);
    }
  },
  mutations: {
    setShoppingCart (state, data) {
   
      state.shoppingCart = data;
    },
    unshiftShoppingCart (state, data) {
   
      state.shoppingCart.unshift(data);
    },
    updateShoppingCart (state, payload) {
    
      if (payload.prop == "num") {
       
        if (state.shoppingCart[payload.key].maxNum < payload.val) {
          return;
        }
        if (payload.val < 1) {
          return;
        }
      }
    
      state.shoppingCart[payload.key][payload.prop] = payload.val;
    },
    addShoppingCartNum (state, cartId) {
      
      for (let i = 0; i < state.shoppingCart.length; i++) {
        const temp = state.shoppingCart[i];
        if (temp.cartId == cartId) {
          if (temp.cartNum < temp.maxNum) {
            temp.cartNum++;
          }
        }
      }
    },
    deleteShoppingCart (state, id) {
      
      for (let i = 0; i < state.shoppingCart.length; i++) {
        const temp = state.shoppingCart[i];
        if (temp.id == id) {
          state.shoppingCart.splice(i, 1);
        }
      }
    },
    checkAll (state, data) {
     
      for (let i = 0; i < state.shoppingCart.length; i++) {
        state.shoppingCart[i].check = data;
      }
    }
  },
  actions: {
    setShoppingCart ({ commit }, data) {
      commit('setShoppingCart', data);
    },
    unshiftShoppingCart ({ commit }, data) {
      commit('unshiftShoppingCart', data);
    },
    updateShoppingCart ({ commit }, payload) {
      commit('updateShoppingCart', payload);
    },
    addShoppingCartNum ({ commit }, productID) {
      commit('addShoppingCartNum', productID);
    },
    deleteShoppingCart ({ commit }, id) {
      commit('deleteShoppingCart', id);
    },
    checkAll ({ commit }, data) {
      commit('checkAll', data);
    }
  }
}
