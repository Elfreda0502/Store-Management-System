<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="Item Name" prop="productName">
        <el-input
          v-model="queryParams.productName"
          placeholder="Please enter Product Name"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="Item Id" prop="productId">
        <el-input
          v-model="queryParams.productId"
          placeholder="Please enter Product Id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Number" prop="number">
        <el-input
          v-model="queryParams.number"
          placeholder="Please enter Number"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Type" prop="type">
        <el-select v-model="queryParams.type" placeholder="Please choose the type" clearable>
          <el-option
            v-for="dict in dict.type.purchase_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="Warehousing time" prop="time">
        <el-date-picker clearable
                        v-model="queryParams.time"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="Please select a storage time">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="Customer" prop="customer">
        <el-input
          v-model="queryParams.customer"
          placeholder="Please enter Customer"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Address" prop="address">
        <el-input
          v-model="queryParams.address"
          placeholder="Please enter Address"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="contacts" prop="contacts">
        <el-input
          v-model="queryParams.contacts"
          placeholder="Please enter contacts"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="PhoneNumber" prop="phoneNumber">
        <el-input
          v-model="queryParams.phoneNumber"
          placeholder="Please enter PhoneNumber"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Unit" prop="unit">
        <el-input
          v-model="queryParams.unit"
          placeholder="Please enter Unit"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="price" prop="price">
        <el-input
          v-model="queryParams.price"
          placeholder="Please enter price"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Remarks" prop="remarks">
        <el-input
          v-model="queryParams.remarks"
          placeholder="Please enter Remarks"
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
          v-hasPermi="['system:purchase:add']"
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
          v-hasPermi="['system:purchase:edit']"
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
          v-hasPermi="['system:purchase:remove']"
        >Delete</el-button>
      </el-col>










      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="purchaseList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="Item ID" align="center" prop="id" />
      <el-table-column label="Item Name" align="center" prop="productName" />

      <el-table-column label="Number" align="center" prop="number" />
      <el-table-column label="Type" align="center" prop="type">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.purchase_type" :value="scope.row.type"/>
        </template>
      </el-table-column>
      <el-table-column label="Warehousing time" align="center" prop="time" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.time, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Customer" align="center" prop="customer">
      </el-table-column>
      <el-table-column label="Address" align="center" prop="address" />
      <el-table-column label="contacts" align="center" prop="contacts" />
      <el-table-column label="PhoneNumber" align="center" prop="phoneNumber" />
      <el-table-column label="Unit" align="center" prop="unit" />
      <el-table-column label="price" align="center" prop="price" />
      <el-table-column label="Remarks" align="center" prop="remarks" />
      <el-table-column label="Operation" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:purchase:edit']"
          >Modify</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:purchase:remove']"
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
        <el-form-item label="Product Name" prop="productName">
          <el-input v-model="form.productName" placeholder="Please enter Product Name" />
        </el-form-item>



        <el-form-item label="Number" prop="number">
          <el-input v-model="form.number" placeholder="Please enter Number" />
        </el-form-item>
        <el-form-item label="Type">
          <el-radio-group v-model="form.type">
            <el-radio
              v-for="dict in dict.type.purchase_type"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="Warehousing time" prop="time">
          <el-date-picker clearable
                          v-model="form.time"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="Please select a storage time">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="Customer" prop="customer">
          <el-input v-model="form.customer" placeholder="Please enter Customer" />
        </el-form-item>
        <el-form-item label="Address" prop="address">
          <el-input v-model="form.address" placeholder="Please enter Address" />
        </el-form-item>
        <el-form-item label="contacts" prop="contacts">
          <el-input v-model="form.contacts" placeholder="Please enter contacts" />
        </el-form-item>
        <el-form-item label="PhoneNumber" prop="phoneNumber">
          <el-input v-model="form.phoneNumber" placeholder="Please enter PhoneNumber" />
        </el-form-item>
        <el-form-item label="Unit" prop="unit">
          <el-input v-model="form.unit" placeholder="Please enter Unit" />
        </el-form-item>
        <el-form-item label="price" prop="price">
          <el-input v-model="form.price" placeholder="Please enter price" />
        </el-form-item>
        <el-form-item label="Remarks" prop="remarks">
          <el-input v-model="form.remarks" placeholder="Please enter Remarks" />
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
import { listPurchase, getPurchase, delPurchase, addPurchase, updatePurchase } from "@/api/store/purchase";

export default {
  name: "Purchase",
  dicts: ['purchase_type'],
  data() {
    return {

      loading: true,

      ids: [],

      single: true,

      multiple: true,

      showSearch: true,

      total: 0,

      purchaseList: [],

      title: "",

      open: false,

      queryParams: {
        pageNum: 1,
        pageSize: 10,
        productName: null,
        productId: null,
        number: null,
        type: null,
        time: null,
        customer: null,
        address: null,
        contacts: null,
        phoneNumber: null,
        unit: null,
        price: null,
        remarks: null
      },

      form: {},

      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {

    getList() {
      this.loading = true;
      listPurchase(this.queryParams).then(response => {
        this.purchaseList = response.rows;
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
        productName: null,
        productId: null,
        number: null,
        type: "0",
        time: null,
        customer: null,
        address: null,
        contacts: null,
        phoneNumber: null,
        unit: null,
        price: null,
        remarks: null
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
      this.title = "Add purchases";
    },

    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getPurchase(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "ModifyPurchase";
      });
    },

    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updatePurchase(this.form).then(response => {
              this.$modal.msgSuccess("Modified successfully");
              this.open = false;
              this.getList();
            });
          } else {
            addPurchase(this.form).then(response => {
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
      this.$modal.confirm('Are you sure to delete the purchase number for"' + ids + '"Data item for？').then(function() {
        return delPurchase(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("Deletion successfully");
      }).catch(() => {});
    },

    handleExport() {
      this.download('system/purchase/export', {
        ...this.queryParams
      }, `purchase_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
