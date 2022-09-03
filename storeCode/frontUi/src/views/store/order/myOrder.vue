<template>
    <div class="box">
        <div>
            <ul class="box-ul">
                <li :class="{ active: isActive === 0 }" @click="getList(0)">待发货</li>
                <li :class="{ active: isActive === 1 }" @click="getList(1)">待收货</li>
                <li :class="{ active: isActive === 2 }" @click="getList(2)">已收货</li>
                <li :class="{ active: isActive === 3 }" @click="getList(3)">已完成</li>
            </ul>
        </div>
        <el-table :data="tableData" border style="width: 100%">
            <el-table-column prop="productName" label="Product picture" width="120px">
                <template slot-scope="scope">
                    <div class="goods">
                        <el-image class="storeImage" style="width: 100px; height: 100px"
                            :src="'http://localhost:9090' + scope.row.productImage" fit="contain"></el-image>
                    </div>
                </template>
            </el-table-column>
            <el-table-column prop="productName" label="Product Name">
                <template slot-scope="scope">
                    <div class="goods">
                        <span class="storeName"> {{ scope.row.productName }}</span>
                    </div>
                </template>
            </el-table-column>
            <el-table-column v-if="isActive >= 1" label="快递公司" align="center" prop="status">
                <template slot-scope="scope">
                    <span>{{ scope.row.deliveryName }} </span>
                </template>
            </el-table-column>
            <el-table-column v-if="isActive >= 1" label="快递订单号" align="center" prop="status">
                <template slot-scope="scope">
                    <span> {{ scope.row.deliveryId }}</span>
                </template>
            </el-table-column>
            <el-table-column v-if="isActive >= 1" label="Distribution method" align="center" prop="status">
                <template slot-scope="scope">
                    <dict-tag :options="dict.type.shipping_type" :value="scope.row.shippingType" />
                </template>
            </el-table-column>
            <el-table-column label="Order status" align="center" prop="status">
                <template slot-scope="scope">
                    <dict-tag :options="dict.type.order_status" :value="scope.row.status" />
                </template>
            </el-table-column>
            <el-table-column prop="price" label="价格">
            </el-table-column>
            <el-table-column label="Operation" align="center" class-name="small-padding fixed-width">
                <template slot-scope="scope">
                    <el-button v-if="isActive == 0 || isActive == 1" size="mini" type="text" icon="el-icon-edit"
                        @click="refundReason(scope.row)">退款</el-button>
                    <el-button v-if="isActive == 3" size="mini" type="text" icon="el-icon-edit"
                        @click="handleUpdate(scope.row)">评价</el-button>
                </template>
            </el-table-column>
        </el-table>




    <el-dialog title="申请退款" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="reform"  label-width="80px">
        <el-form-item label="退款原因" prop="deliveryName">
          <el-input v-model="reform.refundReason" placeholder="Please enter 退款原因" />
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
import { myOrderList, requestRefund } from "@/api/store/order";
export default {
    dicts: ['refund_status', 'shipping_type', 'order_status', 'order_pid_status'],
    data() {
        return {
            tableData: [],
            isActive: 0,
            form: {},
            orderId:null,

            open: false,
            reform: {}
        }
    },
    methods: {
        getMyOrderList() {
            myOrderList().then(res => {
                this.tableData = res.rows
                console.log(res)
            })
        },
        getList(status) {
            console.log(status)
            this.isActive = status
            console.log(status)
            var form = {
                status: status
            }
            myOrderList(form).then(res => {
                this.tableData = res.rows
                console.log(res)
            })
        },

        submitForm(){
            this.reform = {
                'orderId':this.orderId,
                'refundReason':this.reform.refundReason
            }

            requestRefund(this.reform).then(res=>{
                console.log(res)
            })
            console.log()
        },

        refundReason(row){
            console.log('dd',row)
            this.orderId =  row.id
            this.open = true

        },
        // Cancel提交退款原因
        cancel(){
            this.open = false
        }
    },
    created() {
        this.getMyOrderList()
    }

}
</script>

<style >
.active {
    color: red;
}

.box-ul {
    display: flex;
    flex-direction: row;
}

.box-ul li {
    list-style: none;
    flex: 1;
    margin-right: 20px;
    cursor: pointer;
}

.box {
    margin: 50px;
    border: 1px solid #ccc;
}

.box-ul {
    display: flex;
}

/* .goods {
    display: flex;
    align-items: center;
}

.goods .storeName{
    flex: auto;
}
.goods .storeImage img{
    width: 100px;
    height: 100px;
}  */
</style>
