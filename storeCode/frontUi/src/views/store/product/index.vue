<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" >
      <el-form-item label="Product Name" prop="storeName">
        <el-input
          v-model="queryParams.storeName"
          placeholder="Please enter Product Name"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Product introduction" prop="storeInfo">
        <el-input
          v-model="queryParams.storeInfo"
          placeholder="Please enter Product introduction"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Product price" prop="price">
        <el-input
          v-model="queryParams.price"
          placeholder="Please enter Product price"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Sort" prop="sort">
        <el-input
          v-model="queryParams.sort"
          placeholder="Please enter Sort"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="State" prop="isShow">
        <el-select v-model="queryParams.isShow" placeholder="Please select State" clearable>
          <el-option
            v-for="dict in dict.type.product_is_show"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="Cost price" prop="cost">
        <el-input
          v-model="queryParams.cost"
          placeholder="Please enter Cost price"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Virtual sales" prop="ficti">
        <el-input
          v-model="queryParams.ficti"
          placeholder="Please enter Virtual sales"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Views" prop="browse">
        <el-input
          v-model="queryParams.browse"
          placeholder="Please enter Views"
          clearable
          @keyup.enter.native="handleQuery"
        />
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

        >Delete</el-button>
      </el-col>










      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="productList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="Product ID" align="center" prop="id" />
      <el-table-column label="Picture" align="center" prop="image" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.image" :width="50" :height="50"/>
        </template>
      </el-table-column>





      <el-table-column label="Name" align="center" prop="storeName" />

      <el-table-column label="Category" align="center" prop="cateName" />
      <el-table-column label="Price" align="center" prop="price" />
      <el-table-column label="Sort" align="center" prop="sort" />
      <el-table-column label="Sales" align="center" prop="sales" />
      <el-table-column label="Stock" align="center" prop="stock" />
      <el-table-column label="State" align="center" prop="isShow">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.product_is_show" :value="scope.row.isShow"/>
        </template>
      </el-table-column>
      <el-table-column label="Cost price" align="center" prop="cost" />
      <el-table-column label="Virtual sales" align="center" prop="ficti" />
      <el-table-column label="Views" align="center" prop="browse" />
      <el-table-column label="Operation" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"

          >Modify</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"

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
      <div style="display: flex;flex-direction: column">
      <el-form ref="form" :model="form" :rules="rules">
        <div style="display: flex;flex-direction: column">
        <el-form-item label="Product picture">
          <image-upload v-model="form.image"/>
        </el-form-item>
        </div>



        <el-form-item label="Product Name" prop="storeName">
          <el-input v-model="form.storeName" placeholder="Please enter Product Name" />
        </el-form-item>



        <el-form-item label="Product Category" prop="cateId">
          <template>
            <el-select v-model="form.cateId" placeholder="Please select">
              <el-option
                v-for="item in categoryList"
                :key="item.id"
                :label="item.cateName"
                :value="item.id">
              </el-option>
            </el-select>
          </template>

        </el-form-item>
        <el-form-item label="Product price" prop="price">
          <el-input v-model="form.price" placeholder="Please enter Product price" />
        </el-form-item>
        <el-form-item label="Sort" prop="sort">
          <el-input v-model="form.sort" placeholder="Please enter Sort" />
        </el-form-item>
        <el-form-item label="State">
          <el-radio-group v-model="form.isShow">
            <el-radio
              v-for="dict in dict.type.product_is_show"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="Product Description" prop="description">
          <el-input v-model="form.description" type="textarea" placeholder="Please enter  content" />
        </el-form-item>
        <el-form-item label="Product Inventory" prop="description">
          <el-input v-model="form.stock"  placeholder="Please enter Product Inventory" />
        </el-form-item>
        <el-form-item label="Cost price" prop="cost">
          <el-input v-model="form.cost" placeholder="Please enter Cost price" />
        </el-form-item>
        <el-form-item label="Virtual sales" prop="ficti">
          <el-input v-model="form.ficti" placeholder="Please enter Virtual sales" />
        </el-form-item>
        <el-form-item label="Views" prop="browse">
          <el-input v-model="form.browse" placeholder="Please enter Views" />
        </el-form-item>
      </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">Save</el-button>
        <el-button @click="cancel">Cancel</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { listProduct, getProduct, delProduct, addProduct, updateProduct } from "@/api/store/product";
import { listCategory } from "@/api/store/category";

export default {
  name: "Product",
  dicts: ['product_is_show'],
  data() {
    return {

      loading: true,

      ids: [],

      single: true,

      multiple: true,

      showSearch: true,

      total: 0,

      categoryList: [],

      productList: [],

      title: "",

      open: false,

      queryParams: {
        pageNum: 1,
        pageSize: 10,
        image: null,
        sliderImage: null,
        storeName: null,
        storeInfo: null,
        price: null,
        sort: null,
        sales: null,
        stock: null,
        isShow: null,
        description: null,
        cost: null,
        ficti: null,
        browse: null
      },

      form: {},

      rules: {
        image: [
          { required: true, message: "Product picture Cannot be empty", trigger: "blur" }
        ],
        sliderImage: [
          { required: true, message: "轮播图 Cannot be empty", trigger: "blur" }
        ],
        storeName: [
          { required: true, message: "Product Name Cannot be empty", trigger: "blur" }
        ],
        storeInfo: [
          { required: true, message: "商品简介 Cannot be empty", trigger: "blur" }
        ],
        cateId: [
          { required: true, message: "Product Category Cannot be empty", trigger: "blur" }
        ],
        price: [
          { required: true, message: "商品价格 Cannot be empty", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getCategoryList();
  },
  methods: {

    getCategoryList() {
      this.loading = true;
      listCategory().then(response => {
        this.categoryList = response.data;
        console.log(response.data)
        console.log(this.categoryList)
        this.total = response.total;
        this.loading = false;
      });
    },

    getList() {
      this.loading = true;
      listProduct(this.queryParams).then(response => {
        this.productList = response.rows;
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
        image: null,
        sliderImage: null,
        storeName: null,
        storeInfo: null,
        cateId: null,
        price: null,
        sort: null,
        sales: null,
        stock: null,
        isShow: 0,
        description: null,
        cost: null,
        ficti: null,
        browse: null
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
      this.title = "Add product list";
    },

    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getProduct(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "ModifyProduct List";
      });
    },

    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateProduct(this.form).then(response => {
              this.$modal.msgSuccess("Modified successfully");
              this.open = false;
              this.getList();
            });
          } else {
            addProduct(this.form).then(response => {
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
      this.$modal.confirm('Do you want to confirm to delete the product list No. is"' + ids + '"Data item for？').then(function() {
        return delProduct(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("Deletion successfully");
      }).catch(() => {});
    },

    handleExport() {
      this.download('store/product/export', {
        ...this.queryParams
      }, `product_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
