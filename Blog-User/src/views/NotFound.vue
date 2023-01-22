<template>
  <div class="main m-center">
    <h1>:(</h1>
    <p>嗨，朋友！看上去你输入的地址不存在哦~</p>
    <p>你可以回去浏览我的首页~</p>
    <a class="button" href="/">返回首页</a>
  </div>
</template>

<script>
export default {
  // 数据区
  data() {
    return {};
  },
  // 渲染前执行
  created() {},
  // 方法区
  methods: {}
};
</script>

<style scoped>
a {
  color: #3498db;
  text-decoration: none;
}
h1 {
  margin-top: 0;
  font-size: 3.5em;
}

.main {
  color: #333;
  margin: auto;
  padding: 1em;
  display: table;
  user-select: none;
  box-sizing: border-box;
  font: lighter 20px "微软雅黑";
  height: 100% !important;
  width: 100% !important;
  margin: 0 auto;
  text-align: center;
  display: table-cell;
  vertical-align: middle;
}

.m-center {
  width: 50%;
  min-width: 200px;
  height: auto;
  overflow: auto;
  margin: auto;
  position: absolute;
  top: 50%;
  left: 0;
  bottom: 0;
  right: 0;
}

.button {
  color: #fff;
  padding: 0.75em 1em;
  background: #3498db;
  border-radius: 1.5em;
  display: inline-block;
  transition: opacity 0.3s, transform 0.3s;
}
.button:hover {
  transform: scale(1.1);
}
.button:active {
  opacity: 0.7;
}
</style>
