let vm = new Vue({
    el:'#main-container',
    data:{
        office:{
            id:''
        }
    },
    methods:{
        selectOne:function () {
            axios({
                url:`manager/office/selectOne/${this.office.id}`
            }).then(response=>{
                this.office=response.data.obj;
            })
        },
        closeWindow:function () {
            let index=parent.layer.getFrameIndex(window.name);
            parent.layer.close(index)
        }

    },
    created:function () {
        //parent 自动传入的父窗口对象
        //从父窗口的layer对象中获取传递值id赋值到当前update窗口的vue对象中
        this.office.id=parent.layer.obj;
        this.selectOne();
    }
});