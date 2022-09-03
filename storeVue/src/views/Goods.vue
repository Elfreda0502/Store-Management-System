
<template>
  <div class="goods" id="goods" name="goods">

    <div class="breadcrumb">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item to="/">Home</el-breadcrumb-item>
        <el-breadcrumb-item>All Products</el-breadcrumb-item>
        <el-breadcrumb-item v-if="search">Search</el-breadcrumb-item>
        <el-breadcrumb-item v-else>category</el-breadcrumb-item>
        <el-breadcrumb-item v-if="search">{{ search }}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <div class="main">
      <el-col :span="4" :xs="24">
        <div class="category">

          <div class="head-container">
            <el-tree :data="categoryOptions" :props="defaultProps" :expand-on-click-node="false"
              :filter-node-method="filterNode" ref="tree" default-expand-all @node-click="handleNodeClick" />
          </div>
        </div>
      </el-col>
       <el-col :span="20" :xs="24">
      <div class="list">
        <MyList :list="product" v-if="product.length > 0"></MyList>
        <div v-else class="none-product" style="text-align: center">Sorry, no related products were found. Please see other products</div>
      </div>


      <div class="pagination">
        <el-pagination background layout="prev, pager, next" :page-size="pageSize" :total="total"
          @current-change="currentChange"></el-pagination>
      </div>
      </el-col>

    </div>

  </div>
</template>
<script>

export default {
  data() {
    return {
      product: "",
      productList: "",
      total: 0,
      pageSize: 10,
      pageNum: 1,
      search: "",
      categoryOptions: [],
      defaultProps: {
        children: "children",
        label: "cateName"
      },
    };
  },
  created() {

    this.getTreeselect();

    this.getProductList(0);
  },
  activated() {
    this.total = 0;
    this.pageNum = 1;


    if (this.$route.query.search != undefined) {
      console.log("search",this.$route.query.search)
      this.search = this.$route.query.search;
      this.getProductBySearch()
    }
  },
    watch: {

    search: function(val) {
      console.log("Search value:",val)
      if (val != "") {
        this.getProductBySearch(val);
      }
    },

    $route: function(val) {
      if (val.path == "/goods") {
        if (val.query.search != undefined) {
          this.activeName = "-1";
          this.pageNum = 1;
          this.total = 0;
          this.search = val.query.search;
        }
      }
    }

  },
  methods: {
    /** Query product category drop-down tree structure */

    getTreeselect() {
      this.search = ''
      var _this = this;
      this.$axios.get('http://localhost:9090/store/category/list').then(response => {
        console.log(response)
        const data = { id: 0, cateName: 'Commodity classification', children: [] };
        data.children = _this.handleTree(response.data.data, "id", "pid");
        _this.categoryOptions.push(data);
        console.log('DD', _this.categoryOptions)
      })

    },
    handleTree(data, id, parentId, children) {
      let config = {
        id: id || 'id',
        parentId: parentId || 'parentId',
        childrenList: children || 'children'
      };

      var childrenListMap = {};
      var nodeIds = {};
      var tree = [];

      for (let d of data) {
        let parentId = d[config.parentId];
        if (childrenListMap[parentId] == null) {
          childrenListMap[parentId] = [];
        }
        nodeIds[d[config.id]] = d;
        childrenListMap[parentId].push(d);
      }

      for (let d of data) {
        let parentId = d[config.parentId];
        if (nodeIds[parentId] == null) {
          tree.push(d);
        }
      }

      for (let t of tree) {
        adaptToChildrenList(t);
      }

      function adaptToChildrenList(o) {
        if (childrenListMap[o[config.id]] !== null) {
          o[config.childrenList] = childrenListMap[o[config.id]];
        }
        if (o[config.childrenList]) {
          for (let c of o[config.childrenList]) {
            adaptToChildrenList(c);
          }
        }
      }
      return tree;
    },

    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },

    handleNodeClick(data) {
        this.search = ''


      console.log(data.id)
      this.getProductList(data.id)
    },

    getProductList(cartId){
      var _this = this;
      this.$axios.get('http://localhost:9090/store/product/list?cateId=' + cartId + '&pageNum=' + this.pageNum + '&pageSize=' + this.pageSize).then(response => {
        console.log(response)
        _this.product = response.data.rows
        _this.total = response.data.total;
        console.log('DD', _this.categoryOptions)
      })
    },


    backtop() {
      const timer = setInterval(function () {
        const top = document.documentElement.scrollTop || document.body.scrollTop;
        const speed = Math.floor(-top / 5);
        document.documentElement.scrollTop = document.body.scrollTop =
          top + speed;

        if (top === 0) {
          clearInterval(timer);
        }
      }, 20);
    },

    currentChange(pageNum) {
      this.pageNum = pageNum;
      if (this.search != "") {
        this.getProductBySearch();
      } else {
         this.getProductList(0);
      }
      this.backtop();
    },





















    getProductBySearch() {
      this.$axios
        .get("http://localhost:9090/store/product/list?storeName=" + this.search)
        .then(res => {
          this.product = res.data.rows
          this.total = res.data.total;
        })
        .catch(err => {
          return Promise.reject(err);
        });
    }
  }
};
</script>

<style scoped>
.category {
  margin-top: 14.5px;
}
.list{
  margin-top: 14px;
}

.goods {
  background-color: #f5f5f5;
}

/* Breadcrumb CSS */
.el-tabs--card .el-tabs__header {
  border-bottom: none;
}

.goods .breadcrumb {
  height: 50px;
  background-color: white;
}

.goods .breadcrumb .el-breadcrumb {
  width: 1225px;
  line-height: 30px;
  font-size: 16px;
  margin: 0 auto;
}

/* Breadcrumb CSS END */

/* Category label CSS */
.goods .nav {
  background-color: white;
}

.goods .nav .product-nav {
  width: 1225px;
  height: 40px;
  line-height: 40px;
  margin: 0 auto;
}

.nav .product-nav .title {
  width: 50px;
  font-size: 16px;
  font-weight: 700;
  float: left;
}

/* Category label CSS END */

/* Main content area CSS */
.goods .main {
  margin: 0 auto;
  max-width: 1225px;
}



.goods .main .list {
  min-height: 650px;
  margin-left: -13.7px;
  overflow: auto;
}

.goods .main .pagination {
  height: 50px;
  text-align: center;
}

.goods .main .none-product {
  color: #333;
  margin-left: 13.7px;
}

/* Main content area CSS END */
</style>
