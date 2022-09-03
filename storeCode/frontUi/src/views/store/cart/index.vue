<template>
  <div class="app-container">
    <el-table
      v-loading="loading"
      ref="multipleTable"
      :data="list"
      tooltip-effect="dark"
      style="width: 100%"
      @select-all="selectAll"
      @selection-change="handleSelectionChange"
      @select="select"
    >
      <el-table-column type="selection" width="55"> </el-table-column>
      <el-table-column width="600" label="商品">
        <template slot-scope="scope">
          <div class="goods">
            <el-image
              class="storeName"
              style="width: 100px; height: 100px"
              :src="'http://localhost:9090' + scope.row.image"
              fit="contain"
            ></el-image>
            <span class="storeImage"> {{ scope.row.storeName }}</span>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="price" label="价格" width="120"> </el-table-column>
      <el-table-column
        prop="stockText"
        width="120"
        label="Stock"
        show-overflow-tooltip
      >
      </el-table-column>
      <el-table-column label="Number">
        <template slot-scope="scope">
          <el-input-number
            v-model="scope.row.cartNum"
            @change="handleNumberChange(scope.row.id, scope.row.cartNum)"
            :min="1"
          ></el-input-number>
        </template>
      </el-table-column>
    </el-table>
    <div style="margin-top: 20px" class="cart-bottom">
      <el-button style="margin-left: 10px" @click="selectAll()">全选</el-button>
      <div class="cart-bottom-right">
        <p class="cart-bottom-right-count">
          总计 <span>${{ joinSum }}</span>
        </p>
        <p class="cart-bottom-right-go" @click="settlement">去结算 ></p>
      </div>
    </div>

        <el-dialog title="Receiving address" :visible.sync="dialogVisible">

          <el-form ref="form" :model="form" label-width="80px" :visible.sync="dialogVisible">
            <el-form-item label="Receiving address">
          <el-select v-model="orderForm.addressId" placeholder="Please select">
            <el-option
              v-for="item in userAddressList"
              :key="item.id"
              :label="item.consignee + ' ' +item.address "
              :value="item.id">
            </el-option>
          </el-select>

            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">Cancel</el-button>
            <el-button type="primary" @click="goBuy()"
            >Save</el-button
            >
          </div>
        </el-dialog>


  </div>
</template>

<style scoped>
* {
  margin: 0px;
  padding: 0px;
}
.cart-bottom {
  border: 1px solid #ccc;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
}
.cart-bottom-right {
  display: flex;
  flex-direction: row;
  align-items: center;
  float: right;
}
.cart-bottom-right-count {
  flex: 7;
}
.cart-bottom-right-go {
  background: #48d1cc;
  width: 200px;
  height: 100%;
  text-align: center;
  line-height: 100px;
  cursor: pointer;
}

.goods {
  display: flex;
  align-items: center;
}
.storeName {
  width: 100px;
}
.storeImage {
  flex: auto;
}

.app-container {
  margin: 10px;
}
</style>
<script>
import { userListCart, updateCartNum } from "@/api/store/cart";
import { listAddress } from "@/api/user/address";
import { createOrder } from "@/api/store/order";
export default {
  data() {
    return {
      list: [],
      dialogVisible: false,
      multipleSelection: [],
      loading: false,
      joinCartList: [],
      userAddressList: [],
      joinSum: 0,
      form:{},
      formLabelWidth: '120px',
      orderForm:{
        cartId: []
      },

    };
  },
  created() {
    this.getList();
  },

  methods: {
    toggleSelection(rows) {
      if (rows) {
        rows.forEach((row) => {
          this.$refs.multipleTable.toggleRowSelection(row);
        });
      } else {
        this.$refs.multipleTable.toggleAllSelection();
      }
    },
    goBuy(){
      console.log(this.orderForm)
      console.log('dd')
      console.log( this.$refs.multipleTable)
      createOrder(this.orderForm).then(res=>{
        console.log(res)
        if (res.code == 200){
          this.$message({
            message: res.msg,
            type: 'success'
          });
        }
        this.dialogVisible = false
      })
    },

    selectAll(selection) {
      this.joinCartList = selection;
      this.orderForm.cartId = []
      for (var i = 0; i < this.joinCartList.length; i++) {
        console.log('Data',this.joinCartList[i])
        var index = this.orderForm.cartId.indexOf(this.joinCartList[i].id)
        console.log("index",index)
        if (index > -1){
          this.orderForm.cartId.splice(index, 1);
        }else{
          this.orderForm.cartId.push(this.joinCartList[i].id)
        }
        this.joinSum =
          this.joinSum +
          this.joinCartList[i].price * this.joinCartList[i].cartNum;
      }

      console.log(this.orderForm.cartId)
    },

    settlement(){
      this.dialogVisible = true
      listAddress().then(res=>{
        console.log(res)
        this.userAddressList = res.rows
        console.log(this.userListCart)
        console.log('DDD')
      })
    },
    getList() {
      this.loading = true;
      userListCart().then((res) => {
        this.list = res.rows;
        this.loading = false;
      });
    },
    /**
     * Modify购物车数量
     * @param value
     * @param value2
     */
    handleNumberChange(value, value2) {
      this.from = {
        id: value,
        cartNum: value2,
      };
      updateCartNum(this.from).then((res) => {
        console.log(res);
        this.getList();
      });
      this.clearJoinCartData();
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    /**
     * 清空加入购物车的Data
     */
    clearJoinCartData() {
      this.joinCartList = [];
      this.joinSum = 0;
    },
    /**
     * 单选
     * @param selection
     * @param row
     */
    select(selection, row) {
      console.log('单选')
      this.clearJoinCartData();

      console.log('Data',row)

      var index =  this.orderForm.cartId.indexOf(row.id)
      console.log("index",index)
      if (index > -1){
        console.log("Cancel加入购物车")
        this.orderForm.cartId.splice(index, 1);
      }else{
        console.log("加入购物车")
        this.orderForm.cartId.push(row.id)
      }
      this.joinSum =
        this.joinSum +
        row.price * row.cartNum;


      // this.joinCartList = selection;
      // for (var i = 0; i < this.joinCartList.length; i++) {
      //
      // }

      Array.prototype.indexOf = function(val) {
        for (var i = 0; i < this.length; i++) {
          if (this[i] == val) {
            return i;
          }
        }
        return -1;
      };

      Array.prototype.remove = function(val) {
        var index = this.indexOf(val);
        if (index > -1) {
          this.splice(index, 1);
        }
      };



      console.log('cartId', this.orderForm.cartId);
    },

  },
};
</script>

