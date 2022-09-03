<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" >
      <el-form-item label="UserID" prop="uid">
        <el-input
          v-model="queryParams.uid"
          placeholder="Please enter UserID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="商品ID" prop="productId">
        <el-input
          v-model="queryParams.productId"
          placeholder="Please enter 商品ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="商品数量" prop="cartNum">
        <el-input
          v-model="queryParams.cartNum"
          placeholder="Please enter 商品数量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="购买 State" prop="isPay">
        <el-select v-model="queryParams.isPay" placeholder="Please select购买 State" clearable>
          <el-option
            v-for="dict in dict.type.store_cart_is_pay"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">Search</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">Reset</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['cart:cart:add']"
        >Insert</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['cart:cart:edit']"
        >Modify</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['cart:cart:remove']"
        >Delete</el-button>
      </el-col>










      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="cartList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="购物车表ID" align="center" prop="id" />
      <el-table-column label="UserID" align="center" prop="uid" />
      <el-table-column label="商品ID" align="center" prop="productId" />
      <el-table-column label="商品数量" align="center" prop="cartNum" />
      <el-table-column label="购买 State" align="center" prop="isPay">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.store_cart_is_pay" :value="scope.row.isPay"/>
        </template>
      </el-table-column>
      <el-table-column label="Operation" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['cart:cart:edit']"
          >Modify</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['cart:cart:remove']"
          >Delete</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />


    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="UserID" prop="uid">
          <el-input v-model="form.uid" placeholder="Please enter UserID" />
        </el-form-item>
        <el-form-item label="商品ID" prop="productId">
          <el-input v-model="form.productId" placeholder="Please enter 商品ID" />
        </el-form-item>
        <el-form-item label="商品数量" prop="cartNum">
          <el-input v-model="form.cartNum" placeholder="Please enter 商品数量" />
        </el-form-item>
        <el-form-item label="购买 State">
          <el-radio-group v-model="form.isPay">
            <el-radio
              v-for="dict in dict.type.store_cart_is_pay"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">Save</el-button>
        <el-button @click="cancel">Cancel</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listCart, getCart, delCart, addCart, updateCart } from "@/api/store/cart";

export default {
  name: "Cart",
  dicts: ['store_cart_is_pay'],
  data() {
    return {

      loading: true,

      ids: [],

      single: true,

      multiple: true,

      showSearch: true,

      total: 0,

      cartList: [],

      title: "",

      open: false,

      queryParams: {
        pageNum: 1,
        pageSize: 10,
        uid: null,
        productId: null,
        cartNum: null,
        isPay: null
      },

      form: {},

      rules: {
        uid: [
          { required: true, message: "UserID Cannot be empty", trigger: "blur" }
        ],
        productId: [
          { required: true, message: "Product ID Cannot be empty", trigger: "blur" }
        ],
        cartNum: [
          { required: true, message: "Number of Products Cannot be empty", trigger: "blur" }
        ],
        createTime: [
          { required: true, message: "add time Cannot be empty", trigger: "blur" }
        ],
        isPay: [
          { required: true, message: "Purchase status Cannot be empty", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {

    getList() {
      this.loading = true;
      listCart(this.queryParams).then(response => {
        this.cartList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // CancelButton
    cancel() {
      this.open = false;
      this.reset();
    },

    reset() {
      this.form = {
        id: null,
        uid: null,
        productId: null,
        cartNum: null,
        createTime: null,
        updateTime: null,
        isPay: 0
      };
      this.resetForm("form");
    },

    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },

    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },

    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },

    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "add to cart";
    },

    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getCart(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "ModifyShopping Cart";
      });
    },

    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateCart(this.form).then(response => {
              this.$modal.msgSuccess("Modified successfully");
              this.open = false;
              this.getList();
            });
          } else {
            addCart(this.form).then(response => {
              this.$modal.msgSuccess("Insert successfully");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },

    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('Are you sure to delete the shopping cart number as"' + ids + '"Data item for？').then(function() {
        return delCart(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("Deletion successfully");
      }).catch(() => {});
    },

    handleExport() {
      this.download('cart/cart/export', {
        ...this.queryParams
      }, `cart_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
