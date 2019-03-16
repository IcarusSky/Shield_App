charset = "utf-8";
/**
 * HTML5  画图组件
 * @date 2017年3月7日
 * @author LZH
 */
// 保留小数方法
function remainPoint(val, num) {
	var number = num || 0;
	var value = parseFloat(val);
	if (isNaN(value)) { value = 0; }
	return value.toFixed(number);
}
//初始化推进系统Canvas
function TuiJinSystem(canvaId) {

	//一、初始化自定义属性
	//1.用来做屏幕适配的基础值
	TuiJinSystem.prototype.shipei = window.screen.width / 2.8;
	//2.圆的半径
	TuiJinSystem.prototype.R = window.screen.width / 1.3;
	//3.圆心横坐标X
	TuiJinSystem.prototype.centerX = window.screen.width
	//4.圆心纵坐标Y
	TuiJinSystem.prototype.centerY = window.screen.width

	//二、初始化画布属性
	TuiJinSystem.prototype.can = document.getElementById(canvaId);
	/**设置容器高宽为屏幕宽度的一半，画布大小为屏幕的两倍
	 * 相当于在一个容器中显示它4倍大的画布，解决模糊问题
	 */
	this.can.style.width = window.screen.width / 2 + "px"
	this.can.style.height = window.screen.width / 2 + "px"
	this.can.width = window.screen.width * 2;
	this.can.height = window.screen.width * 2;

	//三、初始化画笔属性
	TuiJinSystem.prototype.cans = this.can.getContext('2d');
	this.cans.fillStyle = "blue";
	this.cans.textBaseline = "middle"; //竖直对齐
	this.cans.textAlign = "center"; //水平对齐
	this.cans.strokeStyle = 'black';
	this.cans.lineWidth = this.shipei / 40;
	this.cans.font = this.shipei / 3.5 + "px 微软雅黑";

	//四、初始化自定义方法
	//1.画圆
	TuiJinSystem.prototype.drawCircle = function () {
		var cans = this.cans;
		//边距基础值
		margin = this.shipei / 2;
		//计算圆的弧度
		var radians = (Math.PI / -180) * (360 / 16) //弧度

		for (i = 0; i < 16; i++) {
			//根据圆心和半径计算外圈小圆的圆心
			var x = this.centerX + this.R * Math.sin(radians * i),
				y = this.centerY + this.R * Math.cos(radians * i);
			cans.beginPath();
			//绘制小圆cans.arc(小圆x坐标，小圆y坐标，小圆半径，起始角,结束角，顺时针还是逆时针绘制)
			cans.arc(x, y, this.shipei / 4, 0, Math.PI * 2, false);
			cans.closePath();
			cans.stroke();
			//定义放置 6个土压的坐标
			var x1, y1, x2, y2, x3, y3, x4, y4, x5, y5
			//绘制圆上的序号
			if (i < 9) {
				//绘制圆上的序号 8-16 ,并且同时记录 6个土压的坐标
				cans.fillText(i + 8, x, y);
				if (i == 3) {
					x4 = x;
					y4 = y;
				} else if (i == 5) {
					x5 = x;
					y5 = y;
				}
			} else {
				//绘制圆上的序号 1-7
				cans.fillText(i - 8, x, y);
				if (i == 10) {
					x1 = x;
					y1 = y;
				} else if (i == 14) {
					x3 = x;
					y3 = y;
				} else if (i == 12) {
					x2 = x;
					y2 = y;
				}
			}
			TuiJinSystem.prototype.tuyazuobiao = [
				[x1, y1],
				[x2, y2],
				[x3, y3],
				[x4, y4],
				[x5, y5]
			]
		}

	}
	//2.画bar单位和土压标题
	TuiJinSystem.prototype.drawBar = function () {
		var cans = this.cans;
		x1 = this.tuyazuobiao[0][0];
		y1 = this.tuyazuobiao[0][1];
		x2 = this.tuyazuobiao[1][0];
		y2 = this.tuyazuobiao[1][1];
		x3 = this.tuyazuobiao[2][0];
		y3 = this.tuyazuobiao[2][1];
		x4 = this.tuyazuobiao[3][0];
		y4 = this.tuyazuobiao[3][1];
		x5 = this.tuyazuobiao[4][0];
		y5 = this.tuyazuobiao[4][1];


		// bar 单位距离小圆的距离
		marginBar = this.shipei * 0.4;
		cans.fillStyle = "black";
		cans.fillText("bar", x1 - 0.8 * marginBar, y1 + 1.15 * marginBar);
		cans.fillText("bar", x2 - 1.3 * marginBar, y2);
		cans.fillText("bar", x3 - 0.8 * marginBar, y3 - 1.15 * marginBar);
		cans.fillText("bar", x4 + marginBar * 3.1, y4 - 1.15 * marginBar);
		cans.fillText("bar", x5 + marginBar * 3.1, y5 + marginBar);

		marginTY = this.shipei * 0.3;
		this.cans.font = this.shipei * 0.20 + "px 微软雅黑";
		cans.fillText("1号土压", x1 - 2.5 * marginTY, y1 + 0.5 * marginTY);
		cans.fillText("2号土压", x2 - 2.5 * marginTY, y2 - marginTY);
		cans.fillText("3号土压", x3 - 2.5 * marginTY, y3 - 2.5 * marginTY);
		cans.fillText("4号土压", x4 + 3 * marginTY, y4 - 2.5 * marginTY);
		cans.fillText("5号土压", x5 + 3 * marginTY, y5);
		this.cans.font = this.shipei / 3.5 + "px 微软雅黑";
	}
	//3.画土压数值
	TuiJinSystem.prototype.drawValue = function (value1, value2, value3, value4, value5) {
		x1 = this.tuyazuobiao[0][0];
		y1 = this.tuyazuobiao[0][1];
		x2 = this.tuyazuobiao[1][0];
		y2 = this.tuyazuobiao[1][1];
		x3 = this.tuyazuobiao[2][0];
		y3 = this.tuyazuobiao[2][1];
		x4 = this.tuyazuobiao[3][0];
		y4 = this.tuyazuobiao[3][1];
		x5 = this.tuyazuobiao[4][0];
		y5 = this.tuyazuobiao[4][1];
		//小圆偏移的垂直距离
		marginValue = this.shipei * 0.3;
		this.cans.fillStyle = "blue";
		//一号
		this.cans.fillText(remainPoint(value1, 2), x1 - marginValue * 3.2, y1 + 1.5 * marginValue);
		//二号
		this.cans.fillText(remainPoint(value2, 2), x2 - marginValue * 3.2 - marginValue / 2, y2);
		//三号
		this.cans.fillText(remainPoint(value3, 2), x3 - marginValue * 3.2, y3 - 1.5 * marginValue);
		//四号
		this.cans.fillText(remainPoint(value4, 2), x4 + 2 * marginValue, y4 - 1.5 * marginValue);
		//五号
		this.cans.fillText(remainPoint(value5, 2), x5 + 2 * marginValue, y5 + 1.3 * marginValue);
		this.cans.fillStyle = "black";

	}

	//4.清除画布
	TuiJinSystem.prototype.clearCanvas = function () {
		//重设画布高度和宽度会清空
		this.can.width = this.can.width;
		this.can.height = this.can.height;
		this.cans.fillStyle = "blue";
		this.cans.textBaseline = "middle"; //竖直对齐
		this.cans.textAlign = "center"; //水平对齐
		this.cans.strokeStyle = 'black';
		this.cans.lineWidth = this.shipei / 40;
		this.cans.font = this.shipei / 3.5 + "px 微软雅黑";
	}
}

//初始化导向系统Canvas
function DaoXiangSystem(canvaId) {

	//一、初始化自定义属性
	DaoXiangSystem.prototype.R = Math.round(window.screen.width / 2);
	DaoXiangSystem.prototype.shipei = window.screen.width / 2;
	DaoXiangSystem.prototype.cX = this.R + this.shipei;
	DaoXiangSystem.prototype.cY = this.R + 0.2 * this.shipei;
	DaoXiangSystem.prototype.d = this.R / 5

	//二、初始化画布属性
	DaoXiangSystem.prototype.c = document.getElementById(canvaId);
	this.c.style.width = window.screen.width + "px"
	this.c.style.height = window.screen.width / 1.3 + "px"
	this.c.width = window.screen.width * 2;
	this.c.height = window.screen.width * 2 / 1.3;

	//三、初始化画笔属性
	DaoXiangSystem.prototype.cxt = this.c.getContext("2d");
	this.cxt.lineWidth = this.shipei / 45;
	this.cxt.font = this.shipei / 8 + "px 微软雅黑";

	//四、初始化方法
	//1.画圆
	DaoXiangSystem.prototype.drawCircle = function () {
		var cxt = this.cxt;
		//画圆弧
		cxt.beginPath();
		cxt.arc(this.cX, this.cY, this.R, 0, Math.PI * 2, false);
		cxt.fillStyle = "#77ACFF"; //填充颜色
		cxt.fill(); //画实心圆
		cxt.stroke();
	}
	//2.画XY轴
	DaoXiangSystem.prototype.drawXYZhou = function () {
		var cxt = this.cxt;
		var cX = this.cX;
		var cY = this.cY;
		var R = this.R;
		var d = this.d;
		//画坐标轴
		//Y轴
		cxt.moveTo(cX, cY - R);
		cxt.lineTo(cX, cY + R);
		//X轴
		cxt.moveTo(cX - R, cY);
		cxt.lineTo(cX + R, cY);
		cxt.strokeStyle = 'white';
		cxt.stroke();
		//画横坐标刻度
		cxt.fillStyle = 'white';

		//画X轴刻度
		count = 0;
		for (i = cX - R + d; i < cX + R; i = i + d) {
			switch (count++) {
				case 4:
					continue;
					break;
				case 0:
					cxt.fillText("-200", i - 0.75 * d, cY + 0.74 * d);
					break;
				case 2:
					cxt.fillText("-100", i - 0.75 * d, cY + 0.74 * d);
					break;
				case 6:
					cxt.fillText("100", i - 0.55 * d, cY + 0.74 * d);
					break;
				case 8:
					cxt.fillText("200", i - 0.55 * d, cY + 0.74 * d);
					break;
			}

			cxt.moveTo(i, cY);
			cxt.lineTo(i, cY - d / 3);

		}
		cxt.stroke();

		//画纵坐标刻度
		count = 0;
		for (i = cY - R + d; i < cY + R; i = i + d) {
			switch (count++) {
				case 4:
					continue;
					break;
				case 0:
					cxt.fillText("200", cX - 1.4 * d, i + d / 5);
					break;
				case 2:
					cxt.fillText("100", cX - 1.4 * d, i + d / 5);
					break;
				case 6:
					cxt.fillText("-100", cX - 1.6 * d, i + d / 5);
					break;
				case 8:
					cxt.fillText("-200", cX - 1.6 * d, i + d / 5);
					break;
			}

			cxt.moveTo(cX, i);
			cxt.lineTo(cX + d / 3, i);

		}
		cxt.stroke();
	}
//3.画预警虚线圆形
	DaoXiangSystem.prototype.drawAlarmCircle = function () {
		var cxt = this.cxt;
		var d = this.d;
		var cX = this.cX;
		var cY = this.cY;
		var R = this.R;
		cxt.lineWidth = cxt.lineWidth / 3;
		//画矩形
		cxt.beginPath();
		cxt.setLineDash([4, 3]) //设置虚线，参数为数组，第一个值为实现宽度，第二个值为空白的宽度
		cxt.strokeStyle = "#EA66A1"; /*设置边框*/
		cxt.arc(this.cX, this.cY, 2*d, 0, Math.PI * 2, false);
		cxt.stroke();

		cxt.beginPath();
		cxt.setLineDash([4, 3])
		cxt.strokeStyle = "#F9B800"; /*设置边框*/
		cxt.arc(this.cX, this.cY, d, 0, Math.PI * 2, false);
		cxt.stroke();
		//设置实线
		cxt.setLineDash([])
	}

	//3.画预警虚线矩形
	DaoXiangSystem.prototype.drawRect = function () {
		var cxt = this.cxt;
		var d = this.d;
		var cX = this.cX;
		var cY = this.cY;
		var R = this.R;
		cxt.lineWidth = cxt.lineWidth / 3;
		//画矩形
		cxt.beginPath();
		cxt.setLineDash([4, 3]) //设置虚线，参数为数组，第一个值为实现宽度，第二个值为空白的宽度
		cxt.strokeStyle = "red"; /*设置边框*/
		cxt.strokeRect(cX - 2 * d, cY - 2 * d, 4 * d, 4 * d);
		//设置实线
		cxt.setLineDash([])
	}
	//4.画俯仰角和滚动角
	DaoXiangSystem.prototype.drawFYJandGDJ = function () {
		var d = this.d;
		var shipei = this.shipei;
		var cX = this.cX;
		var cY = this.cY;
		var R = this.R;
		var cxt = this.cxt;

		//画俯仰角和滚动角
		//小刻度 dx
		dx = d / 5;
		//俯仰角起始Y
		fSY = cY - 4 * d;
		//俯仰角起始X
		fSX = cX - R - shipei / 3;
		//俯仰角结束Y
		fEY = cY + 4 * d;
		//俯仰角结束X
		fEX = cX - R - shipei / 3;

		//滚动角起始Y
		gSY = cY + R + shipei / 4;
		//滚动角起始X
		gSX = cX - 7 * d;
		//滚动角结束Y
		gEY = cY + R + shipei / 4;
		//滚动角结束X
		gEX = cX + 7 * d;

		//设置画笔颜色
		cxt.strokeStyle = 'gray';
		//设置填充颜色
		cxt.fillStyle = 'gray';
		cxt.beginPath();
		//画俯仰角线条
		cxt.moveTo(fSX, fSY);
		cxt.lineTo(fEX, fEY);
		cxt.stroke();

		//画滚动角线条
		cxt.moveTo(gSX, gSY);
		cxt.lineTo(gEX, gEY);
		cxt.stroke();

		//画俯仰角大刻度
		//总共有8个刻度
		count = 0;
		for (i = fSY; count <= 8; i = i + d) {
			if (count <= 4) {
				cxt.fillText(-1 * count + 4, fSX - d, i + d / 10)
			} else {
				cxt.fillText(-1 * count + 4, fSX - 1.3 * d, i + d / 10)
			}
			cxt.moveTo(fSX - d / 5, i)
			cxt.lineTo(fEX + d / 5, i);
			count++;
		}
		cxt.stroke();

		//画滚动角大刻度
		//总共有10个刻度
		count = 0;
		for (i = gSX; count <= 14; i = i + d) {
			if (count < 7) {
				cxt.fillText(count - 7, i - d / 2, gSY + d)
			} else {
				cxt.fillText(count - 7, i - d / 4, gSY + d)
			}
			cxt.moveTo(i, gSY + d / 5)
			cxt.lineTo(i, gEY - d / 5);
			count++;
		}
		cxt.stroke();

		//画俯仰角小刻度
		//小刻度
		dx = d / 5;
		count = 0;
		for (i = fSY; count < 40; i = i + dx) {
			cxt.moveTo(fSX, i)
			cxt.lineTo(fEX + d / 7, i);
			count++;
		}
		cxt.stroke();

		//画滚动卷小刻度
		count = 0;
		for (i = gSX; count < 70; i = i + dx) {
			cxt.moveTo(i, gSY)
			cxt.lineTo(i, gEY - d / 7);
			count++;
		}
		cxt.closePath();
		cxt.stroke();
		cxt.fillStyle = '#0304fe';
		//画俯仰角文字
		cxt.fillText("俯仰角:", fEX - 0.35 * shipei, fEY + 0.25 * shipei)

		//画滚动角文字
		cxt.fillText("滚动角:", gEX / 2 - d, gEY + 0.4 * shipei)
	}
	//5.画俯仰角和滚动角数值
	DaoXiangSystem.prototype.drawFYJandGDJValue = function (fyValue, gdValue) {
		var d = this.d;
		var shipei = this.shipei;
		var cX = this.cX;
		var cY = this.cY;
		var R = this.R;
		var cxt = this.cxt;
		cxt.beginPath();
		cxt.strokeStyle = "blue";
		//俯仰角和滚动角0刻度的位置
		fZX = cX - R - shipei / 3;
		fZY = cY;
		gZX = cX;
		gZY = cY + R + shipei / 4;
		//一个d是1
		fyValueY = fZY - fyValue * d;
		gdValueX = gZX + gdValue * d;
		cxt.moveTo(fZX - d / 2, fyValueY);
		cxt.lineTo(fZX + d / 2, fyValueY);

		cxt.moveTo(fZX - d / 2, fyValueY - d / 4);
		cxt.lineTo(fZX - d / 2, fyValueY + d / 4);
		cxt.moveTo(fZX + d / 2, fyValueY - d / 4);
		cxt.lineTo(fZX + d / 2, fyValueY + d / 4);

		cxt.moveTo(gdValueX, gZY - d / 2);
		cxt.lineTo(gdValueX, gZY + d / 2);

		cxt.moveTo(gdValueX + d / 4, gZY - d / 2);
		cxt.lineTo(gdValueX - d / 4, gZY - d / 2);
		cxt.moveTo(gdValueX + d / 4, gZY + d / 2);
		cxt.lineTo(gdValueX - d / 4, gZY + d / 2);
		cxt.closePath();
		cxt.stroke();
		cxt.fillStyle = "#0304fe";
		cxt.fillText(remainPoint(fyValue) + " mm/m", fZX + 0.5 * d, fZY + 5.3 * d);
		cxt.fillText(remainPoint(gdValue) + " mm/m", gZX - 0.3 * d, gZY + 2 * d);
	}
	//6.画切口
	DaoXiangSystem.prototype.drawQK = function (x, y) {
		//一个d是50
		var d = this.d;
		var cX = this.cX;
		var cY = this.cY;
		var cxt = this.cxt;
		var width = d / 2;
		var headx = cX + (x / 50) * d;
		var heady = cY - (y / 50) * d;
		var x1 = headx;
		var y1 = heady - width;
		var x2 = headx + width;
		var y2 = heady;
		var x3 = headx;
		var y3 = heady + width;
		var x4 = headx - width;
		var y4 = heady;

		cxt.beginPath();
		cxt.lineWidth = 3;
		cxt.strokeStyle = "red";
		cxt.moveTo(x1, y1);
		cxt.lineTo(x3, y3);
		cxt.moveTo(x2, y2);
		cxt.lineTo(x4, y4);
		cxt.closePath();
		cxt.stroke();
	}
	//7.画盾尾
	DaoXiangSystem.prototype.drawDw = function (x, y) {
		var d = this.d;
		var cX = this.cX;
		var cY = this.cY;
		var cxt = this.cxt;

		var width = d / 2;
		var headx = cX + (x / 50) * d;
		var heady = cY - (y / 50) * d;

		var x1 = headx - width;
		var y1 = heady - width;
		var x2 = headx + width;
		var y2 = heady - width;
		var x3 = headx + width;
		var y3 = heady + width;
		var x4 = headx - width;
		var y4 = heady + width;

		cxt.beginPath();
		cxt.lineWidth = 3;
		cxt.strokeStyle = "#0c88ce";
		cxt.moveTo(x1, y1);
		cxt.lineTo(x3, y3);
		cxt.moveTo(x2, y2);
		cxt.lineTo(x4, y4);
		cxt.closePath();
		cxt.stroke();
	}
	//8.画切口和盾尾的线
	DaoXiangSystem.prototype.drawFx = function (hx, hy, fx, fy) {
		var d = this.d;
		var shipei = this.shipei;
		var cX = this.cX;
		var cY = this.cY;
		var cxt = this.cxt;
		var headx = cX + (hx / 50) * d;
		var heady = cY - (hy / 50) * d;
		var footx = cX + (fx / 50) * d;
		var footy = cY - (fy / 50) * d;

		cxt.beginPath();
		cxt.lineWidth = 2;
		cxt.strokeStyle = "#FAFAFA";
		cxt.moveTo(headx, heady);
		cxt.lineTo(footx, footy);
		cxt.closePath();
		cxt.stroke();
	}

	//9.清除画布
	DaoXiangSystem.prototype.clearCanvas = function () {
		//重设画布高度和宽度会清空
		this.c.width = this.c.width;
		this.c.height = this.c.height;
		this.cxt.lineWidth = this.shipei / 45;
		this.cxt.font = this.shipei / 8 + "px 微软雅黑";
	}
	//10.画纸飞机
	DaoXiangSystem.prototype.drawAirPlane = function (hx, hy, fx, fy) {
		var d = this.d;
		var shipei = this.shipei;
		var cX = this.cX;
		var cY = this.cY;
		var cxt = this.cxt;
		var headx = cX + (hx / 50) * d;
		var heady = cY - (hy / 50) * d;
		var footx = cX + (fx / 50) * d;
		var footy = cY - (fy / 50) * d;


		//求出盾头和盾尾之间的距离用来做纸飞机大小的适配
		var calX = headx - footx;
		var calY = heady - footy;
		var dis = Math.pow((calX * calX + calY * calY), 0.5);

		cxt.beginPath()
		//设置线条颜色和填充颜色
		cxt.strokeStyle = '#0000FF';
		cxt.fillStyle = 'rgba(235,235,235,0.95)';
		//设置阴影颜色
		cxt.shadowColor = "#363636";
		//设置阴影偏移量和模糊度
		cxt.shadowOffsetX = 10;
		cxt.shadowOffsetY = 15;
		cxt.shadowBlur = 30;
		//一 计算点坐标,共八个点
		//1.DT-盾头点
		var DT = new Array(headx, heady);
		//2.DW-盾尾点
		var DW = new Array(footx, footy);
		//3.S-上面的点
		var SX = footx
		var SY = footy - d;
		var S = new Array(SX, SY);
		//4.X-下面的点
		var XX = footx
		var XY = footy + d;
		var X = new Array(XX, XY);
		//5.Z-左边的点
		var ZX = footx - d;
		var ZY = footy;
		var Z = new Array(ZX, ZY);
		//6.Y-右边的点
		var YX = footx + d;
		var YY = footy;
		var Y = new Array(YX, YY);

		//纸飞机分两种情况:当headx>footx,飞机倾斜向右边;当headx<footx,飞机倾斜向左边
		//情况一:当headx>footx,飞机倾斜向右边，先画左三角形，避免阴影遮挡
		if (headx > footx) {
			//二 根据点坐标画三角形,共四个
			//1.左三角形
			cxt.beginPath()
			this.drawTriangle(cxt, DW, Z, DT);
			cxt.fill();
			cxt.stroke();
			cxt.closePath();
			//2.上三角形
			cxt.beginPath()
			this.drawTriangle(cxt, DT, DW, S);
			cxt.fill();
			cxt.stroke();
			cxt.closePath();
			//3.下三角形
			cxt.beginPath()
			this.drawTriangle(cxt, DW, X, DT);
			cxt.fill()
			cxt.stroke();
			cxt.closePath();
			//4.右三角形
			cxt.beginPath()
			this.drawTriangle(cxt, DT, DW, Y);
			cxt.fill();
			cxt.stroke();
			cxt.closePath();
		}
		//情况二:当headx<footx,飞机倾斜向左边,先画右边
		else if (headx <= footx) {
			//设置阴影偏移量和模糊度
			cxt.shadowOffsetX = -10;
			cxt.shadowOffsetY = +15;
			cxt.shadowBlur = 30;
			//1.右三角形
			cxt.beginPath()
			this.drawTriangle(cxt, DT, DW, Y);
			cxt.fill();
			cxt.stroke();
			cxt.closePath();
			//2.上三角形
			cxt.beginPath()
			this.drawTriangle(cxt, DT, DW, S);
			cxt.fill();
			cxt.stroke();
			cxt.closePath();
			//3.下三角形
			cxt.beginPath()
			this.drawTriangle(cxt, DW, X, DT);
			cxt.fill()
			cxt.stroke();
			cxt.closePath();
			//4.左三角形
			cxt.beginPath()
			this.drawTriangle(cxt, DW, Z, DT);
			cxt.fill();
			cxt.stroke();
			cxt.closePath();

		}
	}
	//11.画三角形--主要给画纸飞机调用
	DaoXiangSystem.prototype.drawTriangle = function (cxt, A, B, C) {
		//“A、B、C”是顶点
		with (cxt) {
			moveTo(A[0], A[1]);
			lineTo(B[0], B[1]);
			lineTo(C[0], C[1]);
			lineTo(A[0], A[1]);
		}
	}
}