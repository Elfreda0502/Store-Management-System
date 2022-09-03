<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" >
      <el-form-item label="Order No" prop="orderId">
        <el-input v-model="queryParams.orderId" placeholder="Please enter  the order No" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>

      <el-form-item label="Payment time" prop="payTime">
        <el-date-picker clearable v-model="queryParams.payTime" type="date" value-format="yyyy-MM-dd"
          placeholder="Please select Payment time">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="Order status" prop="status">
        <el-select v-model="queryParams.status" placeholder="Please select Order status" clearable>
          <el-option v-for="dict in dict.type.order_status" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>








      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">Search</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">Reset</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['system:order:remove']">Delete</el-button>
      </el-col>




      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="orderList" @selection-change="handleSelectionChange">
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form  class="demo-table-expand">




            <el-form-item label="Order No">
              <div>{{ props.row.orderId}}</div>
            </el-form-item>
            <el-form-item label="Refund user instructions">
              <div>{{ props.row.refundReasonWapExplain}}</div>
            </el-form-item>
            <el-table-column label="Refund time" align="center" prop="refundReasonTime" width="180">

                <span>{{ parseTime(props.row.refundReasonTime, '{y}-{m}-{d}') }}</span>

            </el-table-column>
            <el-form-item label="Reason for front desk refund">
              <div>{{ props.row.refundReasonWap}}</div>
            </el-form-item>
            <el-form-item label="Reason for non refund">
              <span>{{ props.row.refundReason}}</span>
            </el-form-item>
            <el-form-item label="Refund amount">
              <span>{{ props.row.refundPrice}}</span>
            </el-form-item>



            <el-form-item label="Express name">
              <span>{{ props.row.deliveryName}}</span>
            </el-form-item>



            <el-form-item label="Express Number">
              <span>{{ props.row.deliveryId}}</span>
            </el-form-item>
            <el-form-item label="Verification Code">
              <span>{{ props.row.verifyCode}}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>


      <el-table-column type="selection" width="55" align="center" />




      <el-table-column label="User name" align="center" prop="realName" />
      <el-table-column label="Store Name" align="center" prop="storeName" />



      <el-table-column label="Total orders" align="center" prop="totalNum" />
      <el-table-column label="Total order price" align="center" prop="totalPrice" />


      <el-table-column label="Payment status" align="center" prop="paid">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.order_pid_status" :value="scope.row.paid" />
        </template>
      </el-table-column>
      <el-table-column label="Payment time" align="center" prop="payTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.payTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Order status" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.order_status" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column label="Refund status" align="center" prop="refundStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.refund_status" :value="scope.row.refundStatus" />
        </template>
      </el-table-column>











      <el-table-column label="Distribution method" align="center" prop="shippingType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.shipping_type" :value="scope.row.shippingType" />
        </template>
      </el-table-column>
      <el-table-column label="Operation" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-if="scope.row.status == 0" size="mini" type="text" icon="el-icon-edit"
            @click="handleUpdate(scope.row)" v-hasPermi="['system:order:edit']">Deliver goods</el-button>
          <el-button v-if="scope.row.refundStatus == 1" size="mini" type="text" icon="el-icon-edit"
            @click="refund(scope.row)" v-hasPermi="['system:order:edit']">Review refunds</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['system:order:remove']">Delete</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />


    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <div style="display: flex;flex-direction: column">
      <el-form ref="form" :model="form" :rules="rules">




        <el-form-item label="Express name" prop="deliveryName">
          <el-input v-model="form.deliveryName" placeholder="Please enter courier name" />
        </el-form-item>
        <el-form-item label="Express Number/PhoneNumber" prop="deliveryId">
          <el-input v-model="form.deliveryId" placeholder="Please enter courier number" />
        </el-form-item>
        <el-form-item label="Distribution method" prop="shippingType">
          <el-select v-model="form.shippingType" placeholder="Please select a shipping method">
            <el-option v-for="dict in dict.type.shipping_type" :key="dict.value" :label="dict.label"
              :value="parseInt(dict.value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="Verification Code" prop="deliverySn">
          <el-input v-model="form.verifyCode" placeholder="Please enter Verification code (self-pickup)" />
        </el-form-item>
      </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">Save</el-button>
        <el-button @click="cancel">Cancel</el-button>
      </div>
    </el-dialog>




    <el-dialog title="Review refunds" :visible.sync="openRefund" width="500px" append-to-body>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="refundOk">Refund</el-button>
        <el-button @click="refundFail">No refund</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listOrder, getOrder, delOrder, addOrder, updateOrder, deliverGoods, orderRefund,orderRefundFail } from "@/api/store/order";

export default {
  name: "Order",
  dicts: ['refund_status', 'shipping_type', 'order_status', 'order_pid_status'],
  data() {
    return {

      loading: true,

      ids: [],

      single: true,

      multiple: true,

      showSearch: true,

      total: 0,

      orderList: [],

      title: "",

      open: false,
      openRefund: false,
      refundParams: {
        id: '',
      },

      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderId: null,
        extendOrderId: null,
        uid: null,
        realName: null,
        userPhone: null,
        userAddress: null,
        cartId: null,
        freightPrice: null,
        totalNum: null,
        totalPrice: null,
        totalPostage: null,
        payPrice: null,
        payPostage: null,
        deductionPrice: null,
        couponId: null,
        couponPrice: null,
        paid: null,
        payTime: null,
        payType: null,
        status: null,
        refundStatus: null,
        refundReasonWapImg: null,
        refundReasonWapExplain: null,
        refundReasonTime: null,
        refundReasonWap: null,
        refundReason: null,
        refundPrice: null,
        deliverySn: null,
        deliveryName: null,
        deliveryType: null,
        deliveryId: null,
        cost: null,
        verifyCode: null,
        storeId: null,
        shippingType: null
      },

      form: {},

      rules: {
        orderId: [
          { required: true, message: "order number Cannot be empty", trigger: "blur" }
        ],
        uid: [
          { required: true, message: "Userid Cannot be empty", trigger: "blur" }
        ],
        realName: [
          { required: true, message: "UserName Cannot be empty", trigger: "blur" }
        ],
        userPhone: [
          { required: true, message: "UserPhone Cannot be empty", trigger: "blur" }
        ],
        userAddress: [
          { required: true, message: "Address Cannot be empty", trigger: "blur" }
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
      listOrder(this.queryParams).then(response => {
        this.orderList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    refund(row) {
      console.log(row.id)
      this.refundParams.id = row.id
      this.openRefund = true
    },

    refundOk() {
      orderRefund(this.refundParams).then(res => {
        console.log(res)
        this.$modal.msgSuccess(res.msg);
        this.openRefund = false;
        this.getList();
      })

    },

    refundFail() {
      orderRefundFail(this.refundParams).then(res=>{
        this.$modal.msgSuccess(res.msg);
        this.openRefund = false;
        this.getList();

      })
    },
    // CancelButton
    cancel() {
      this.open = false;
      this.reset();
    },

    reset() {
      this.form = {
        id: null,
        orderId: null,
        extendOrderId: null,
        uid: null,
        realName: null,
        userPhone: null,
        userAddress: null,
        cartId: null,
        freightPrice: null,
        totalNum: null,
        totalPrice: null,
        totalPostage: null,
        payPrice: null,
        payPostage: null,
        deductionPrice: null,
        couponId: null,
        couponPrice: null,
        paid: null,
        payTime: null,
        payType: null,
        createTime: null,
        updateTime: null,
        status: null,
        refundStatus: null,
        refundReasonWapImg: null,
        refundReasonWapExplain: null,
        refundReasonTime: null,
        refundReasonWap: null,
        refundReason: null,
        refundPrice: null,
        deliverySn: null,
        deliveryName: null,
        deliveryType: null,
        deliveryId: null,
        cost: null,
        verifyCode: null,
        storeId: null,
        shippingType: null
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
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },

    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "Add order";
    },

    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getOrder(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "ModifyOrder";
      });
    },

    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            deliverGoods(this.form).then(response => {
              this.$modal.msgSuccess("Modified successfully");
              this.open = false;
              this.getList();
            });
          } else {
            addOrder(this.form).then(response => {
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
      this.$modal.confirm('Are you sure to delete the order number as"' + ids + '"Data item for？').then(function () {
        return delOrder(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("Deletion successfully");
      }).catch(() => { });
    },

    handleExport() {
      this.download('system/order/export', {
        ...this.queryParams
      }, `order_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
