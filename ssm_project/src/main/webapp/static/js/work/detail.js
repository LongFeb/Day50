let vm = new Vue({
    el:'#main-container',
    data:{
        work:{
            id:'',
            details:[{
                plateNumber:''
            }]
        }
    },
    methods:{
        selectOne:function () {
            axios({
                url:`manager/admin/work/selectDetail/${this.work.id}`
            }).then(response=>{
                this. work=response.data.obj;
            })
        }
    },
    created:function () {
        //parent 自动传入的父窗口对象
        //从父窗口的layer对象中获取传递值id赋值到当前update窗口的vue对象中
        this. work.id=parent.layer.obj;
        this.selectOne();
    }
});