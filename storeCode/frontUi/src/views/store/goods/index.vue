<template>
  <div>
    <div class="category">
      <!-- MenuData-->
     <el-col :span="6" :xs="24">
        <div class="head-container">
          <el-tree
            :data="categoryOptions"
            :props="defaultProps"
            :expand-on-click-node="false"
            :filter-node-method="filterNode"
            ref="tree"
            default-expand-all
            @node-click="handleNodeClick"
          />
        </div>
     </el-col>
    </div>
    <div class="product">
      <div class="productBox" v-for="(item, index) in productList" :key="index2">
        <img class="image" :src="'http://localhost:9090' + item.image" alt="" @click="toProductDetail(item)" />
        <p class="productName">{{ item.storeName }}</p>
        <div class="price">
          <span class="presentPrice">RM {{ item.price }}</span>

        </div>
        <div class="line"></div>
        <p class="productDesc">{{ item.storeInfo }}</p>

      </div>
    </div>

  </div>

</template>
<script>
import { listProduct } from "@/api/store/product";
import {treeselect} from "@/api/system/dept";
import {listCategory} from "@/api/store/category";
import { addCart } from "@/api/store/cart";
export default {
  data  () {
    return {
      productList: [],
      categoryOptions: [],
      categoryName: '',
      defaultProps: {
        children: "children",
        label: "cateName"
      },
      img: './static/addCart.png',

      queryParams: {
        cateName: null,
        cateId: null,
        sort: null,
        pic: null,
        isDel: null
      },
      form: {},
    }
  },
  created() {
    this.getList();
    this.getTreeselect()
  },
  methods: {

    getList() {
      this.loading = true;
      listProduct(this.queryParams).then(response => {
        this.productList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    addCart(productId){
      console.log(productId)
      this.form = {
        id: null,
        uid: null,
        productId: productId,
        cartNum: 1,
      };
      addCart(this.form).then(res =>{
        console.log(res)
      })

    },

    getTreeselect() {
      listCategory().then(response => {
        const data = { id: 0, cateName: 'Product category', children: [] };
        data.children = this.handleTree(response.data, "id", "pid");
        this.categoryOptions.push(data);
        console.log('DD', this.categoryOptions)
      });
    },

    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },

    handleNodeClick(data) {
      this.queryParams.cateId = data.id;
      this.handleQuery();
    },

    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    toProductDetail (item) {
      let data = {
        productId: item.id
      }
      this.$router.push({
        path: '/productDetail',
        query: {
          proData: JSON.stringify(data)
        }
      })
    }
  }
}
</script>
<style lang = "scss" scoped>
.addCart{
  font-size: 36px;
  position: absolute;
  left: 276px;
  top: 393px;
}
.product{

  margin: 20px;
  display: flex;
  flex-wrap: wrap;
  flex-direction: row;
}
.productBox{
  position: relative;
  margin: 10px;
  padding: 10px;
  width: 300px;
  height: 430px;
  border: 1px solid #ccc;
}
.image{
  width: 280px;
  text-align: center;

}
.productName{
  text-align: center;
  font-weight: bold;
}
.price {
  margin-bottom: 12px;
  text-align: center;
  .presentPrice {
    font-size: 18px;
    font-weight: bold;
    color: #ff7800;
  }
  .originalPrice {
    font-size: 16px;
    text-decoration: line-through;
    margin-left: 10px;

  }
}
.productDesc{
  text-align: center;
  color: #999999;
}
.line {
  width: 160px;
  height: 2px;
  background-color: #f0f0f0;
  margin: 0 auto;
}
</style>
