
<template>
  <div class="confirmOrder">

    <div class="confirmOrder-header">
      <div class="header-content">
        <p>
          <i class="el-icon-s-order"></i>
        </p>
        <p>Confirm order</p>
        <router-link to></router-link>
      </div>
    </div>



    <div class="content">

      <div class="section-address">
        <p class="title">Receiving address</p>
        <div class="address-body">
          <ul>
            <li :class="item.id == confirmAddress ? 'in-section' : ''" v-for="item in address" :key="item.id"
              @click="selectAddress(item.id)">
              <h2>{{ item.consignee }}</h2>
              <p class="phone">{{ item.receivingNumber }}</p>
              <p class="address">{{ item.address }}</p>
               <p style="color:red;cursor:pointer" @click="delUserAddress(item.id)">Delete</p>
            </li>
            <li class="add-address" @click="addAddress">
              <i class="el-icon-circle-plus-outline"></i>
              <p>add new address</p>
            </li>
          </ul>
        </div>
      </div>


     <el-dialog title="Add address" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form"  label-width="80px">
        <el-form-item label="Receiving Address" prop="address">
          <el-input v-model="form.address" placeholder="Please enter Receiving address" />
        </el-form-item>
        <el-form-item label="Receiver" prop="consignee">
          <el-input v-model="form.consignee" placeholder="Please enter the consignee" />
        </el-form-item>
        <el-form-item label="Phone No" prop="receivingNumber">
          <el-input v-model="form.receivingNumber" placeholder="Please enter your mobile number" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">Save</el-button>
        <el-button @click="cancel">Cancel</el-button>
      </div>
    </el-dialog>


      <div class="section-goods">
        <p class="title">Products</p>
        <div class="goods-list">
          <ul>
            <li v-for="item in getCheckGoods" :key="item.id">
              <img :src="$target + item.image" />
              <span class="pro-name">{{ item.productName }}</span>
              <span class="pro-price">RM {{ item.price }} x {{ item.cartNum }}</span>
              <span class="pro-status"></span>
              <span class="pro-total">RM {{ (item.price * item.cartNum).toFixed(2) }}</span>
            </li>
          </ul>
        </div>
      </div>



      <div class="section-shipment">
        <p class="title">Distribution method</p>
        <p class="shipment" >
            <el-radio v-model="radio" label="1">Express delivery</el-radio>
            <el-radio v-model="radio" label="2">In-store pickup</el-radio>
        </p>
      </div>



      <div class="section-invoice">
        <p class="title">Invoice</p>
        <p class="invoice">Electronic invoice</p>
        <p class="invoice">Personal</p>
        <p class="invoice">Product details</p>
      </div>



      <div class="section-count">
        <div class="money-box">
          <ul>
            <li>
              <span class="title">number of goods：</span>
              <span class="value">{{ getCheckNum }} piece</span>
            </li>
            <li>
              <span class="title">Total price of goods：</span>
              <span class="value">RM {{ getTotalPrice }}</span>
            </li>
            <li>
              <span class="title">Activity Offer：</span>
              <span class="value">-RM 0</span>
            </li>
            <li>
              <span class="title">Coupon deduction：</span>
              <span class="value">-RM 0</span>
            </li>
            <li>
              <span class="title">Freight：</span>
              <span class="value">RM 0</span>
            </li>
            <li class="total">
              <span class="title">Total payable：</span>
              <span class="value">
                <span class="total-price">RM {{ getTotalPrice }}</span>
              </span>
            </li>
          </ul>
        </div>
      </div>



      <div class="section-bar">
        <div class="btn">
          <router-link to="/shoppingCart" class="btn-base btn-return">Return to shopping cart</router-link>
          <a href="javascript:void(0);" @click="addOrder" class="btn-base btn-primary">Settlement</a>
        </div>
      </div>

    </div>

  </div>
</template>
<script>
import { mapGetters } from "vuex";
import { mapActions } from "vuex";
export default {
  name: "",
  data() {
    return {
      open:false,
      radio:"1",
      form: {},

      confirmAddress: 1,

      address: [],
      cartId: []
    };
  },
  created() {

    if (this.getCheckNum < 1) {
      this.notifyError("Please check the item before settlement");
      this.$router.push({ path: "/shoppingCart" });
    }
    this.getUserAddress()
  },
  computed: {

    ...mapGetters(["getCheckNum", "getTotalPrice", "getCheckGoods"])
  },
  methods: {
    ...mapActions(["deleteShoppingCart"]),
    addOrder() {
      this.cartId = []
      console.log(this.getCheckGoods)
      for (var i = 0; i < this.getCheckGoods.length; i++) {
        console.log(this.getCheckGoods[i].id)
        this.cartId.push(this.getCheckGoods[i].id)
      }
      console.log(this.cartId)





      this.$axios
        .post("http://localhost:9090/store/order/createOrder", {
          "cartId": this.cartId,
          "shippingType":this.radio,
          "addressId": this.confirmAddress
        })
        .then(res => {
          if (res.data.code == 200) {
            let products = this.getCheckGoods;
            console.log(res.data)
            console.log(products)






            this.notifySucceed(res.data.msg);

            this.$router.push({ path: "/order" });
          } else {
            this.notifyError(res.data.msg);
          }

        })
        .catch(err => {
          return Promise.reject(err);
        });
    },

    delUserAddress(id){
      console.log(id)
       this.$axios.get('http://localhost:9090/user/address/del/' + id).then(res => {
         this.notifySucceed(res.data.msg);
        console.log(res.data)
        this.getUserAddress()

      })
    },
    addAddress() {
      console.log("Open popup..")
      this.open = true

    },
    submitForm(){
      console.log(this.form)
      console.log("Added..")
       this.$axios.post('http://localhost:9090/user/address',this.form).then(res => {
        console.log(res.data)
        this.getUserAddress()
         this.notifySucceed(res.data.msg);
      })
       this.open = false
    },
    cancel(){
      console.log('Cancel')
       this.open = false
    },
    getUserAddress() {
      this.$axios.get('http://localhost:9090/user/address/list?pageNum=1&pageSize=10', {
        headers: {
          "Authorization": localStorage.getItem('token')
        }
      }).then(res => {
        this.address = res.data.rows
        console.log(res.data)
      })
    },
    selectAddress(id) {
      this.confirmAddress = id
    }
  }
};
</script>
<style scoped>
.confirmOrder {
  background-color: #f5f5f5;
  padding-bottom: 20px;
}

/* header CSS */
.confirmOrder .confirmOrder-header {
  background-color: #fff;
  border-bottom: 2px solid #ff6700;
  margin-bottom: 20px;
}

.confirmOrder .confirmOrder-header .header-content {
  width: 1225px;
  margin: 0 auto;
  height: 80px;
}

.confirmOrder .confirmOrder-header .header-content p {
  float: left;
  font-size: 28px;
  line-height: 80px;
  color: #424242;
  margin-right: 20px;
}

.confirmOrder .confirmOrder-header .header-content p i {
  font-size: 45px;
  color: #ff6700;
  line-height: 80px;
}

/* header CSS END */

/* Main content container CSS */
.confirmOrder .content {
  width: 1225px;
  margin: 0 auto;
  padding: 48px 0 0;
  background-color: #fff;
}

/* Select address CSS */
.confirmOrder .content .section-address {
  margin: 0 48px;
  overflow: hidden;
}

.confirmOrder .content .section-address .title {
  color: #333;
  font-size: 18px;
  line-height: 20px;
  margin-bottom: 20px;
}

.confirmOrder .content .address-body li {
  float: left;
  color: #333;
  width: 220px;
  height: 178px;
  border: 1px solid #e0e0e0;
  padding: 15px 24px 0;
  margin-right: 17px;
  margin-bottom: 24px;
}

.confirmOrder .content .address-body .in-section {
  border: 1px solid #ff6700;
}

.confirmOrder .content .address-body li h2 {
  font-size: 18px;
  font-weight: normal;
  line-height: 30px;
  margin-bottom: 10px;
}

.confirmOrder .content .address-body li p {
  font-size: 14px;
  color: #757575;
}

.confirmOrder .content .address-body li .address {
  padding: 10px 0;
  /*max-width: 180px;*/
  /*max-height: 88px;*/
  /*line-height: 22px;*/
  overflow: hidden;
}

.confirmOrder .content .address-body .add-address {
  text-align: center;
  line-height: 30px;
}

.confirmOrder .content .address-body .add-address i {
  font-size: 30px;
  padding-top: 50px;
  text-align: center;
}

/* Select address CSS END */

/* Merchandise and coupon CSS */
.confirmOrder .content .section-goods {
  margin: 0 48px;
}

.confirmOrder .content .section-goods p.title {
  color: #333;
  font-size: 18px;
  line-height: 40px;
}

.confirmOrder .content .section-goods .goods-list {
  padding: 5px 0;
  border-top: 1px solid #e0e0e0;
  border-bottom: 1px solid #e0e0e0;
}

.confirmOrder .content .section-goods .goods-list li {
  padding: 10px 0;
  color: #424242;
  overflow: hidden;
}

.confirmOrder .content .section-goods .goods-list li img {
  float: left;
  width: 30px;
  height: 30px;
  margin-right: 10px;
}

.confirmOrder .content .section-goods .goods-list li .pro-name {
  float: left;
  width: 650px;
  line-height: 30px;
}

.confirmOrder .content .section-goods .goods-list li .pro-price {
  float: left;
  width: 150px;
  text-align: center;
  line-height: 30px;
}

.confirmOrder .content .section-goods .goods-list li .pro-status {
  float: left;
  width: 99px;
  height: 30px;
  text-align: center;
  line-height: 30px;
}

.confirmOrder .content .section-goods .goods-list li .pro-total {
  float: left;
  width: 190px;
  text-align: center;
  color: #ff6700;
  line-height: 30px;
}

/* Merchandise and coupon CSS END */

/* Shipping method CSS */
.confirmOrder .content .section-shipment {
  margin: 0 48px;
  padding: 25px 0;
  border-bottom: 1px solid #e0e0e0;
  overflow: hidden;
}



.confirmOrder .content .section-shipment .title {
  float: left;
  width: 250px;
  color: #333;
  font-size: 18px;
  line-height: 38px;
}

.confirmOrder .content .section-shipment .shipment {
  float: left;
  width: 250px;
  line-height: 38px;
  font-size: 14px;
  color: #ff6700;
}

/* Shipping method CSS END */

/* Invoice CSS */
.confirmOrder .content .section-invoice {
  margin: 0 48px;
  padding: 25px 0;
  border-bottom: 1px solid #e0e0e0;
  overflow: hidden;
}

.confirmOrder .content .section-invoice .title {
  float: left;
  width: 250px;
  color: #333;
  font-size: 18px;
  line-height: 38px;
}

.confirmOrder .content .section-invoice .invoice {
  float: left;
  line-height: 38px;
  font-size: 14px;
  margin-right: 20px;
  color: #ff6700;
}

/* Invoice CSS END */

/* settlement list CSS */
.confirmOrder .content .section-count {
  margin: 0 48px;
  padding: 20px 0;
  overflow: hidden;
}

.confirmOrder .content .section-count .money-box {
  float: right;
  text-align: right;
}

.confirmOrder .content .section-count .money-box .title {
  float: left;
  width: 180px;
  height: 30px;
  display: block;
  line-height: 30px;
  color: #757575;
}

.confirmOrder .content .section-count .money-box .value {
  float: left;
  min-width: 105px;
  height: 30px;
  display: block;
  line-height: 30px;
  color: #ff6700;
}

.confirmOrder .content .section-count .money-box .total .title {
  padding-top: 15px;
}

.confirmOrder .content .section-count .money-box .total .value {
  padding-top: 10px;
}

.confirmOrder .content .section-count .money-box .total-price {
  font-size: 30px;
}

/* Settlement list CSS END */

/* Settlement navigation CSS */
.confirmOrder .content .section-bar {
  padding: 20px 48px;
  border-top: 2px solid #f5f5f5;
  overflow: hidden;
}

.confirmOrder .content .section-bar .btn {
  float: right;
}

.confirmOrder .content .section-bar .btn .btn-base {
  float: left;
  margin-left: 30px;
  width: 158px;
  height: 38px;
  border: 1px solid #b0b0b0;
  font-size: 14px;
  line-height: 38px;
  text-align: center;
}

.confirmOrder .content .section-bar .btn .btn-return {
  color: rgba(0, 0, 0, 0.27);
  border-color: rgba(0, 0, 0, 0.27);
}

.confirmOrder .content .section-bar .btn .btn-primary {
  background: #ff6700;
  border-color: #ff6700;
  color: #fff;
}

/* Settlement navigation CSS */

/* Main content container CSS END */
</style>
