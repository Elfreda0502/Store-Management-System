
<template>
  <div class="order">

    <div class="order-header">
      <div class="order-header-content">
        <p>
          <i class="el-icon-s-order" style="font-size: 30px;color: #ff6700;"></i>
          My Order
        </p>
      </div>
    </div>

    <div class="order-type">
      <ul class="box-ul">
        <li :class="{ active: isActive === 0 }" @click="getList(0)">To be shipped</li>
        <li :class="{ active: isActive === 1 }" @click="getList(1)">To be received</li>
        <!-- <li :class="{ active: isActive === 2 }" @click="getList(2)">Received goods</li> -->
        <li :class="{ active: isActive === 3 }" @click="getList(3)">Completed</li>
            <li :class="{ active: isActive === -3 }" @click="getList(-3)">Refunded</li>
      </ul>
    </div>


    <div class="order-content" v-if="orders.length > 0">
      <div class="content" v-for="(item, index) in orders" :key="index">
        <ul>

          <li class="order-info">
            <div class="order-id">Order No: {{ item.id }}</div>
            <div class="order-time">Order time: {{ item.createTime | dateFormat }}</div>
          </li>
          <li class="header">
            <div class="pro-img"></div>
            <div class="pro-name">ProductName</div>
            <div class="pro-price">Price</div>
            <div class="pro-num">Number</div>
            <div class="pro-total">Subtotal</div>
            <div class="pro-op">Operation</div>

          </li>



          <li class="product-list">
            <div class="pro-img">
              <router-link :to="{ path: '/goods/details', query: { productID: item.productId } }">
                <img :src="$target + item.productImage" />
              </router-link>
            </div>
            <div class="pro-name">
              <router-link :to="{ path: '/goods/details', query: { productID: item.productId } }">
                {{ item.productName }}</router-link>
            </div>
            <div class="pro-price">RM {{ item.onePrice }}</div>
            <div class="pro-num">{{ item.cartNum }}</div>
            <div class="pro-total pro-total-in">RM {{ (item.cartNum * item.onePrice).toFixed(2) }}</div>
            <div class="pro-op">
              <el-button v-if="isActive === 1" size="mini" class="btn" @click="ok(item.id)" type="success">Receiving Completed</el-button>
              <el-button size="mini" class="btn" @click="refund(item.id)" type="info">Refund</el-button>
            </div>
          </li>
        </ul>
        <div class="order-bar">
          <div class="order-bar-left">
            <span class="order-total">
              Common
              <span class="order-total-num">{{ item.cartNum }}</span> Items,
            </span>
            <span v-if="isActive > 0">
              <span style="color:#757575"> Distribution method </span>
              <span style="color:#ff6700">{{ item.shippingType == 1 ? 'Express transportation' : 'Store self collection' }}</span>
              <span style="color:#757575"> {{ item.shippingType == 1 ? ', Courier Number:' : ', Self-withdrawal Verification Code:' }} </span>
              <span style="color:#ff6700">{{ item.shippingType == 1 ? item.deliveryId : item.verifyCode }}</span>
            </span>

          </div>
          <div class="order-bar-right">
            <span>
              <span class="total-price-title">Total：</span>
              <span class="total-price">RM {{ (item.price).toFixed(2) }}</span>
            </span>
          </div>

        </div>
      </div>
      <div style="margin-top:-40px;"></div>
    </div>



    <div v-else class="order-empty">
      <div class="empty">
        <h2>Your order is still empty！</h2>
        <p>Go shopping！</p>
      </div>
    </div>





    <el-dialog title="Apply for refund" :visible.sync="open" width="500px" append-to-body>
      <div style="display: flex;flex-direction: column">
      <el-form ref="form">
        <el-form-item label="Apply for refund" prop="deliveryName">
          <el-input v-model="refundReason" placeholder="Please enter the refund reason" />
        </el-form-item>
      </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">Save</el-button>
        <el-button @click="open = false">Cancel</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
export default {
  data() {
    return {
      orders: [],
      total: [],
      isActive: 0,
      open: false,
      reform: {},
      refundId: null,
      refundReason: ''
    };
  },
  activated() {

    this.$axios
      .get("http://localhost:9090/store/order/myOrderList?status=0", {
      })
      .then(res => {
        if (res.data.code === 200) {
          console.log(res.data.rows)
          this.orders = res.data.rows;
        } else {
          this.notifyError(res.data.msg);
        }
      })
      .catch(err => {
        return Promise.reject(err);
      });
  },
  methods: {
    getList(status) {
      this.isActive = status
      this.$axios
        .get("http://localhost:9090/store/order/myOrderList?status=" + status, {
        })
        .then(res => {
          if (res.data.code === 200) {
            console.log(res.data.rows)
            this.orders = res.data.rows;
          } else {
            this.notifyError(res.data.msg);
          }
        })
        .catch(err => {
          return Promise.reject(err);
        });
    },

    refund(id) {
      console.log(id)
      this.refundId = id
      this.open = true
    },
    ok(id){
            this.$axios
        .get("http://localhost:9090/store/order/orderOk?orderId=" + id )
        .then(res => {
         this.notifySucceed(res.data.msg)
         this.getList(this.isActive)
        })
        .catch(err => {
          return Promise.reject(err);
        });
      
    },

    submitForm() {
      this.$axios
        .post("http://localhost:9090/store/order/requestRefund", {
          orderId: this.refundId,
          refundReason: this.refundReason
        })
        .then(res => {
          if (res.data.code === 200) {
            console.log(res.data)
            this.notifySucceed(res.data.msg)
              this.getList(0)
          } else {
            this.notifyError(res.data.msg);
          }
        })
        .catch(err => {
          return Promise.reject(err);
        });
        this.open = false

    }
  },
  watch: {

    orders: function (val) {
      let total = [];
      for (let i = 0; i < val.length; i++) {
        const element = val[i];

        let totalNum = 0;
        let totalPrice = 0;
        for (let j = 0; j < element.length; j++) {
          const temp = element[j];
          totalNum += temp.product_num;
          totalPrice += temp.product_price * temp.product_num;
        }
        total.push({ totalNum, totalPrice });
      }
      this.total = total;
    }
  }
};
</script>
<style scoped>
.btn{
  margin-bottom: 10px;
 margin-left: 0px;
 height: 35px;

}
.order {
  background-color: #f5f5f5;
  padding-bottom: 20px;
}

.active {
  color: #ff6700;
}

.order-type {
  /* border: 1px solid #ccc; */
  width: 1225px;
  margin: 0px auto;
  padding-top: 10px;
  padding-bottom: 10px;
  background-color: #fff;
}

.box-ul {
  display: flex;
  flex-direction: row;
}

.box-ul li {
  /* border: 1px solid #ccc; */
  list-style: none;
  flex: 1;
  margin-right: 20px;
  cursor: pointer;
  text-align: center;
}

/* My order header CSS */
.order .order-header {
  height: 64px;
  border-bottom: 2px solid #ff6700;
  background-color: #fff;
  margin-bottom: 20px;
}

.order .order-header .order-header-content {
  width: 1225px;
  margin: 0 auto;
}

.order .order-header p {
  font-size: 28px;
  line-height: 58px;
  float: left;
  font-weight: normal;
  color: #424242;
}

/* My order header CSS END */
.order .content {
  width: 1225px;
  margin: 0 auto;
  background-color: #fff;
  margin-bottom: 50px;
}

.order .content ul {
  background-color: #fff;
  color: #424242;
  line-height: 85px;
}

/* My order header CSS */
.order .content ul .order-info {
  height: 60px;
  line-height: 60px;
  padding: 0 26px;
  color: #424242;
  border-bottom: 1px solid #ff6700;
}

.order .content ul .order-info .order-id {
  float: left;
  color: #ff6700;
}

.order .content ul .order-info .order-time {
  float: right;
}

.order .content ul .header {
  display: flex;
  height: 85px;
  padding-right: 26px;
  color: #424242;
}

/* My order header CSS END */

/* Order list CSS */
.order .content ul .product-list {
  display: flex;
  height: 85px;
  padding: 15px 26px 15px 0;
  border-top: 1px solid #e0e0e0;
}

.order .content ul .pro-img {
  /* float: left; */
  height: 85px;
  width: 120px;
  padding-left: 80px;
}

.order .content ul .pro-img img {
  height: 80px;
  width: 80px;
}

.order .content ul .pro-name {
  flex: 4;
  /* float: left; */
  width: 380px;
}

.order .content ul .pro-name a {
  color: #424242;
}

.order .content ul .pro-name a:hover {
  color: #ff6700;
}

.order .content ul .pro-price {
  flex: 1;
  /* float: left; */
  /* width: 160px; */
  padding-right: 18px;
  text-align: center;
}

.order .content ul .pro-num {
  flex: 1;
  /* float: left; */
  /* width: 190px; */
  text-align: center;
}

.order .content ul .pro-total {
  /* float: left; */
  flex: 1;
  /* width: 160px; */
  padding-right: 81px;
  text-align: right;
}

.order .content ul .pro-op {
  display: flex;
    flex-direction: column;
    justify-content: center;
  flex: 1;
  text-align: center;
  /* padding-right: 81px; */
}

.order .content ul .pro-total-in {
  color: #ff6700;
}

.order .order-bar {
  width: 1185px;
  padding: 0 20px;
  border-top: 1px solid #ff6700;
  height: 50px;
  line-height: 50px;
  background-color: #fff;
}

.order .order-bar .order-bar-left {
  float: left;
}

.order .order-bar .order-bar-left .order-total {
  color: #757575;
}

.order .order-bar .order-bar-left .order-total-num {
  color: #ff6700;
}

.order .order-bar .order-bar-right {
  float: right;
}

.order .order-bar .order-bar-right .total-price-title {
  color: #ff6700;
  font-size: 14px;
}

.order .order-bar .order-bar-right .total-price {
  color: #ff6700;
  font-size: 30px;
}

/* Order List CSS END */

/* CSS displayed when the order is empty */
.order .order-empty {
  width: 1225px;
  margin: 0 auto;
}

.order .order-empty .empty {
  height: 300px;
  padding: 0 0 130px 558px;
  margin: 65px 0 0;
  /* background: url(../assets/imgs/cart-empty.png) no-repeat 124px 0; */
  color: #b0b0b0;
  overflow: hidden;
}

.order .order-empty .empty h2 {
  margin: 70px 0 15px;
  font-size: 36px;
}

.order .order-empty .empty p {
  margin: 0 0 20px;
  font-size: 20px;
}

/* CSS displayed when the order is empty END */
</style>
