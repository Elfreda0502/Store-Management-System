<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" >
      <el-form-item label="CategoryName" prop="cateName">
        <el-input
          v-model="queryParams.cateName"
          placeholder="Please enter CategoryName"
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
      <el-form-item label="Icon" prop="pic">
        <el-input
          v-model="queryParams.pic"
          placeholder="Please enter Icon"
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
          type="info"
          plain
          icon="el-icon-sort"
          size="mini"
          @click="toggleExpandAll"
        >Expand / collapse</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      v-if="refreshTable"
      v-loading="loading"
      :data="categoryList"
      row-key="id"
      :default-expand-all="isExpandAll"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column label="CategoryName" align="center" prop="cateName" />
      <el-table-column label="Sort" align="center" prop="sort" />
      <el-table-column label="Icon" align="center" prop="pic" />
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
            icon="el-icon-plus"
            @click="handleAdd(scope.row)"

          >Insert</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"

          >Delete</el-button>
        </template>
      </el-table-column>
    </el-table>


    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <div style="display: flex;flex-direction: column">
      <el-form ref="form" :model="form" :rules="rules">
        <el-form-item label="parent id" prop="pid">
          <treeselect v-model="form.pid" :options="categoryOptions" :normalizer="normalizer" placeholder="Please select parent id" />
        </el-form-item>
        <el-form-item label="CategoryName" prop="cateName">
          <el-input v-model="form.cateName" placeholder="Please enter CategoryName" />
        </el-form-item>
        <el-form-item label="Sort" prop="sort">
          <el-input v-model="form.sort" placeholder="Please enter Sort" />
        </el-form-item>
        <el-form-item label="Icon" prop="pic">

          <div class="smart">
              <el-cascader
                class="test"
                :options="options"
                :props="{ multiple: true, checkStrictly: true}"
                clearable></el-cascader>
          </div>


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

<style lang="scss" scoped>
/*.smart{*/
/*  background-color: #48D1CC;*/
/*}*/
.el-cascader-menu__list{
  display: flex;
  flex-wrap: wrap;
}
::v-deep el-scrollbar__view el-cascader-menu__list{
  display: flex;
  flex-wrap: wrap;
}

</style>
<script>
import { listCategory, getCategory, delCategory, addCategory, updateCategory } from "@/api/store/category";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Category",
  components: {
    Treeselect
  },
  data() {
    return {

      loading: true,

      showSearch: true,

      categoryList: [],

      categoryOptions: [],

      title: "",

      open: false,

      isExpandAll: true,

      refreshTable: true,

      queryParams: {
        cateName: null,
        sort: null,
        pic: null,
        isDel: null,
        cateId:null,
      },

      form: {},

      rules: {
        pid: [
          { required: true, message: "父id Cannot be empty", trigger: "blur" }
        ],
        cateName: [
          { required: true, message: "分类 Name Cannot be empty", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {

    getList() {
      this.loading = true;
      listCategory(this.queryParams).then(response => {
        this.categoryList = this.handleTree(response.data, "id", "pid");
        this.loading = false;
      });
    },

    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.id,
        label: node.cateName,
        children: node.children
      };
    },

    getTreeselect() {
      listCategory().then(response => {
        this.categoryOptions = [];
        const data = { id: 0, cateName: 'Top node', children: [] };
        data.children = this.handleTree(response.data, "id", "pid");
        this.categoryOptions.push(data);
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
        pid: null,
        cateName: null,
        sort: null,
        pic: null,
        isShow: null,
        createTime: null,
        updateTime: null,
        isDel: null
      };
      this.resetForm("form");
    },

    handleQuery() {
      this.getList();
    },

    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },

    handleAdd(row) {
      this.reset();
      this.getTreeselect();
      if (row != null && row.id) {
        this.form.pid = row.id;
      } else {
        this.form.pid = 0;
      }
      this.open = true;
      this.title = "Add product category";
    },

    toggleExpandAll() {
      this.refreshTable = false;
      this.isExpandAll = !this.isExpandAll;
      this.$nextTick(() => {
        this.refreshTable = true;
      });
    },

    handleUpdate(row) {
      this.reset();
      this.getTreeselect();
      if (row != null) {
        this.form.pid = row.id;
      }
      getCategory(row.id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "Modify product category";
      });
    },

    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateCategory(this.form).then(response => {
              this.$modal.msgSuccess("Modified successfully");
              this.open = false;
              this.getList();
            });
          } else {
            addCategory(this.form).then(response => {
              this.$modal.msgSuccess("Insert successfully");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },

    handleDelete(row) {
      this.$modal.confirm('Whether to confirm to delete the product classification number is"' + row.id + '"data item？').then(function() {
        return delCategory(row.id);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("Deletion successfully");
      }).catch(() => {});
    }
  }
};
</script>
