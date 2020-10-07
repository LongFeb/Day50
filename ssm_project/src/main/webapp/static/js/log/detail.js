let vm = new Vue({
    el:'#main-container',
    data:{
        log:{
            id:''
        }
    },
    methods:{
        selectOne:function () {
            axios({
                url:`manager/syslog/selectOne/${this.log.id}`
            }).then(response=>{
                this.log=response.data.obj;
            })
        },

    },
    created:function () {
        this.log.id=parent.layer.obj;
        this.selectOne();
    }
});