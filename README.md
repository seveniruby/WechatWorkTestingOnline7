# WechatWorkTestingOnline7
霍格沃兹测试学院第七期学员演练项目，企业微信的接口测试实战

# 设计思路

测试框架核心
- API对象：完成对接口的封装
- 接口测试框架：完成对api的驱动
- 配置模块：完成配置文件的读取
- 数据封装：数据构造与测试用例的数据封装
- Utils：其他功能封装，改进原生框架不足
- 测试用例：调用Page/API对象实现业务并断言

测试用例构建体系
- 使用package管理业务模块
- 使用class管理业务功能
- 使用method完成业务具体行为
- 使用配置文件读取初始配置
- 使用继承规划用例执行顺序
- 使用testcase完成测试用例的落地
- 使用assertion完成业务正确性校验
- 使用数据文件管理用例的数据驱动
- 使用jenkins完成持续集成


# 参考连接
https://testerhome.com/topics/17969