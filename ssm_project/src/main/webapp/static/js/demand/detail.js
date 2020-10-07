let vm = new Vue({
    el:'#main-container',
    data:{
        demand:{
            id:''
        }
    },
    methods:{
        selectOne:function () {
            axios({
                url:`manager/demand/selectOne/${this.demand.id}`
            }).then(response=>{
                this.demand=response.data.obj;
            })
        },
        closeWindow:function () {
            let index=parent.layer.getFrameIndex(window.name);
            parent.layer.close(index)
        }

    },
    created:function () {
        this.demand.id=parent.layer.obj;
        this.selectOne();
    }
});