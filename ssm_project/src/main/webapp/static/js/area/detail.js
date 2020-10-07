let vm = new Vue({
    el:'#main-container',
    data:{
        area:{
            id:''
        }
    },
    methods:{
        selectOne:function () {
            axios({
                url:`manager/area/selectOne/${this.area.id}`
            }).then(response=>{
                this.area=response.data.obj;
            })
        },
        // doUpdate:function () {
        //     axios({
        //         url:`manager/area/doUpdate`,
        //         method:'put',
        //         data: this.area
        //     }).then(response=>{
        //         /**
        //          * 更新失败，提示失败信息，不关闭窗口
        //          * 更新成功，关闭子窗口  在父窗口弹出提示信息
        //          */
        //         if(response.data.success){
        //             parent.layer.message=response.data.obj; //父窗口弹出信息
        //             let index=parent.layer.getFrameIndex(window.name); //获取窗口索引
        //             parent.layer.close(index) //通过窗口索引关闭子窗口
        //         }else {
        //             layer.msg(response.data.msg)
        //         }
        //     }).catch(error=>{
        //         layer.msg(error);
        //     })
        // },
        closeWindow:function () {
            let index=parent.layer.getFrameIndex(window.name);
            parent.layer.close(index)
        }

    },
    created:function () {
        //parent 自动传入的父窗口对象
        //从父窗口的layer对象中获取传递值id赋值到当前update窗口的vue对象中
        this.area.id=parent.layer.obj;
        this.selectOne();
    }
});