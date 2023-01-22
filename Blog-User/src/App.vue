<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
export default {
  name: "App",
  // 创建前给body添加事件
  created() {
    $("body").click(function(e) {
      // 出现的文字数组
      const text = [
        "富强",
        "民主",
        "文明",
        "和谐",
        "自由",
        "平等",
        "公正",
        "法治",
        "爱国",
        "敬业",
        "诚信",
        "友善"
      ];
      // 颜色数组
      const color = [
        "#ff0000",
        "#eb4310",
        "#fbb417",
        "#ffff00",
        "#cdd541",
        "#99cc33",
        "#3f9337",
        "#219167",
        "#24998d",
        "#1f9baa",
        "#0080ff",
        "#3366cc",
        "#333399",
        "#003366",
        "#800080",
        "#a1488e",
        "#c71585",
        "#bd2158",
        "#eec142"
      ];
      // 要显示的文字、颜色下标
      const textIndex = Math.floor((Math.random() * 20) % text.length);
      const colorIndex = Math.floor((Math.random() * 40) % color.length);
      // 获取点击的位置
      const x = e.pageX;
      const y = e.pageY;
      const $textElement = $("<span />").text(text[textIndex]);
      $textElement.css({
        "z-index": 9999,
        top: y - 20,
        left: x,
        position: "absolute",
        "font-weight": "bold",
        color: color[colorIndex]
      });
      $("body").append($textElement);
      $textElement.animate(
        {
          top: y - 180,
          opacity: 0
        },
        1500,
        function() {
          $textElement.remove();
        }
      );
    });
  },
  methods: {}
};
</script>

<style scoped>
#app {
  height: 100%;
}
</style>
