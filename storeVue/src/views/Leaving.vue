
<template>
    <div class="leaving" id="leaving" name="leaving">
        <div class="send_leaving">

            <textarea v-model="message"></textarea>
            <br>
            <el-button class="send" @click="sendLeaving" type="primary">Post a message</el-button>
        </div>
        <div class="list">
            <ul>
                <li class="item" v-for="item in list" :key="item">
                    <div class="userInfo">
                        <div class="nickname">
                            <span class="name" style="color:#f60">{{ item.nickname }}:</span>
                            <span> {{ item.leavingMessage }}</span>
                        </div>
                        <div class="time">
                            <span>Message time </span>
                            <span style="color:#f60">{{ item.leavingTime }}</span>
                        </div>
                    </div>


                    <div class="userInfo" v-if="item.replyTime">
                        <div class="nickname">
                            <span class="name" style="color:#f60">Admin:</span>
                            <span>{{ item.replyMessage }}</span>

                        </div>
                        <div class="time">
                            <span>Reply time </span>
                            <span style="color:#f60">{{ item.replyTime }}</span>
                        </div>
                    </div>
                    <div class="userInfo" v-if="!item.replyTime">
                        <div class="nickname">
                            <span class="name" style="color:#f60">Admin:</span>
                            <span>No reply yet</span>

                        </div>
                        <div class="time">
                            <span>Reply time </span>
                            <span style="color:#f60">No reply yet</span>
                        </div>
                    </div>

                </li>
            </ul>
        </div>

    </div>
</template>
<script>

export default {
    data() {
        return {
            message: '',
            list: []
        };
    },
    created() {
        this.getList()

    },
    methods: {

        sendLeaving() {
            this.$axios.post("http://localhost:9090/store/leaving", {
                'leavingMessage': this.message
            }).then(res => {
                this.getList()
                this.notifySucceed(res.data.msg)

            })
        },
        getList() {
            this.$axios.get("http://localhost:9090/store/leaving/list?pageNum=1&pageSize=10").then(res => {
                console.log(res.data.rows)
                this.list = res.data.rows
            })
        }
    }
};
</script>

<style scoped>
.userInfo {
    display: flex;
    justify-content: space-between;
}

.userInfo .nickname .name {
    flex: 1;
    /* width: 40px; */
    display: block;
    /* border: 1px solid red; */
}

.list {
    margin-top: 10px;
}

.list .item {
    border-radius: 10px;
    margin-bottom: 20px;
    cursor: pointer;
    padding: 10px;
    border: 1px solid #ccc;
}

.item .content {
    color: black;
    /* text-align: center; */
}

.list .reply {
    text-align: center;
    color: #f6060f;
}

.leaving {
    /* border: 1px solid #ccc; */
    width: 1225px;
    margin: 0px auto;
}

.send_leaving textarea {
    color: #f6060f;
    font-size: 15px;
    height: 100px;
    width: 1215px;
    margin-bottom: 10px;
    outline: none;
    padding: 10px;
    border: 1px solid #ccc;
    resize: none;
    border-radius: 10px;
}

.send_leaving .send {
    width: 1225px;
}
</style>
