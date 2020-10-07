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
                this.area.oldParentIds=this.area.parentIds;
            })
        },
        toSelect:function(){
            layer.open({
                type: 2,    // 弹出类型  2为iframe  会在当前页面上动态生成一个dom，以iframe方式写入
                title: false,//子窗口标题    false则不显示
                area: ['80%', '80%'],        //窗口大小  [宽,高]   支持px和占据父窗口比例
                content: 'manager/area/toSelect', //iframe的url
                end: () => {

                    if(layer.parentName != undefined && layer.parentName != ''){//获取子窗口传递的icon
                        this.area.parentName = layer.parentName;
                        this.area.parentId = layer.parentId;
                        this.area.parentIds=layer.parentIds;
                    }
                }
            });
        },
        toModules:function(){
            layer.open({
                type: 2,    // 弹出类型  2为iframe  会在当前页面上动态生成一个dom，以iframe方式写入
                title: false,//子窗口标题    false则不显示
                area: ['80%', '80%'],        //窗口大小  [宽,高]   支持px和占据父窗口比例
                content: 'manager/area/toModules', //iframe的url
                end: () => {

                    if(layer.icon != undefined && layer.icon != ''){//获取子窗口传递的icon
                        this.area.icon = layer.icon;
                    }
                }
            });
        },
        doUpdate:function () {
            axios({
                url:`manager/area/doUpdate`,
                method:'put',
                data: this.area
            }).then(response=>{
                /**
                 * 更新失败，提示失败信息，不关闭窗口
                 * 更新成功，关闭子窗口  在父窗口弹出提示信息
                 */
                if(response.data.success){
                    parent.layer.message=response.data.obj; //父窗口弹出信息
                    let index=parent.layer.getFrameIndex(window.name); //获取窗口索引
                    parent.layer.close(index) //通过窗口索引关闭子窗口
                }else {
                    layer.msg(response.data.msg)
                }
            }).catch(error=>{
                layer.msg(error.message);
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
        this.area.id=parent.layer.obj;
        this.selectOne();
    }
});