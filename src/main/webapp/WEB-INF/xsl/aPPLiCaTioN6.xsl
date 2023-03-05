<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:output
		method="html"
		encoding="UTF-8"
		omit-xml-declaration="yes"
		doctype-public="-//W3C//DTD HTML 4.01 Transitional//EN"
		doctype-system="http://www.w3.org/TR/html4/loose.dtd"
		indent="no"
		media-type="text/html"
	/>

	<xsl:include href="/welcome.xsl"/>

	<!--文件-->
	<xsl:template match="/">
		<HTML lang="zh-TW" dir="ltr">
			<xsl:apply-templates select="document"/>
		</HTML>
	</xsl:template>

	<!--根-->
	<xsl:template match="document">
		<HEAD>
			<META http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
			<META http-equiv="Content-Language" content="zh-TW"/>
			<META http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
			<LINK href="{@contextPath}/STYLE/welcome.css" rel="stylesheet" type="text/css"/>
			<STYLE type="text/css">.aPPLiCaTioN OL,.aPPLiCaTioN UL{margin:16px 0;padding-left:16px;list-style-position:inside}.aPPLiCaTioN B{font-weight:bold}.aPPLiCaTioN U{text-decoration:underline}.aPPLiCaTioN CAPTION{color:#C00;text-align:center}.aPPLiCaTioN .must:before{content:'*';color:#AB4025}.aPPLiCaTioN TH:after{content:'：'}.aPPLiCaTioN TH{text-align:left;white-space:nowrap}</STYLE>
			<SCRIPT type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/{@jQueryVersion}/jquery.min.js"/>
			<SCRIPT type="text/javascript" src="{@contextPath}/SCRIPT/welcome.js"/>
			<TITLE>苗栗產業創新推動中心&#187;活動報名</TITLE>
		</HEAD>
		<BODY>
			<DIV id="container">
				<!--頭-->
				<xsl:apply-templates select="header">
					<xsl:with-param name="contextPath" select="@contextPath"/>
				</xsl:apply-templates>

				<!--橫幅-->
				<DIV id="banner">
					<IMG src="{@contextPath}/images/banner1.jpg" alt=""/>
					<IMG src="{@contextPath}/images/banner2.jpg" alt=""/>
					<IMG src="{@contextPath}/images/banner3.jpg" alt=""/>
				</DIV>

				<!--容器-->
				<DIV id="wrapper">
					<!--左側區塊之圖片連結-->
					<DIV id="aside">
						<UL>
							<xsl:for-each select="aside/*">
								<LI>
									<A title="{@title}" href="{.}" target="_blank">
										<IMG src="{../../@contextPath}/link/{@id}.png" alt="{@title}" height="52" width="176"/>
									</A>
								</LI>
							</xsl:for-each>
						</UL>
					</DIV>

					<!--左側區塊之計數器-->
					<DIV class="counter">
						<xsl:for-each select="counter/*">
							<SPAN>
								<xsl:value-of select="."/>
							</SPAN>
						</xsl:for-each>
					</DIV>

					<!--左側區塊之底下那張圖-->
					<DIV class="asidebg">
						<IMG src="{@contextPath}/IMG/qrCode.png" alt=""/>
					</DIV>

					<!--內容-->
					<DIV id="content">
						<DIV class="aPPLiCaTioN">
							<H1 style="color:#00F;font-size:160%">2016解密科技寶藏 - 創新科技專案體驗【企業日】</H1>
							<H2 style="color:#F00;font-size:150%">～體驗16法人研發成果，參與跨領域交流、激盪～</H2>
							<P>經濟部技術處透過創新科技專案提供產業發展的實踐科技，結合16法人共同展出60項創新科技專案研發成果及20項科技美學產品概念。特別安排企業日由研發人員現身說法，與來訪業界專家進行交流；對特定技術項目有興趣進一步洽談者，亦可安排互訪，探討技術落實產業應用的合作機會。</P>
							<H3 style="color:#00F;font-size:140%;text-align:underline">企業專屬之導覽與洽商，名額有限，敬請預約參與！</H3>
							<UL>
								<LI>
									<SPAN style="color:#973520">指導單位：</SPAN>
									<SPAN>經濟部技術處</SPAN>
									‬</LI>
								<LI>
									<SPAN style="color:#973520">共同主辦：</SPAN>
									<SPAN>16家創新科技專案研發機構(請參見105年展出技術一覽表)</SPAN>
									‬</LI>
								<LI>
									<SPAN style="color:#973520">展期：</SPAN>
									<SPAN>105年7月27日星期三</SPAN>
									‬</LI>
								<LI>
									<SPAN style="color:#973520">展出地點：</SPAN>
									<SPAN>台北三創生活園區12樓(地址：台北市中正區市民大道三段2號12樓)</SPAN>
									‬</LI>
								<LI>
									<SPAN style="color:#973520">企業日參觀時間/行程/上車地點A、B：</SPAN>
									<SPAN>上車地點A：7/27(三)12:15~18:00</SPAN>
									<TABLE border="1" cellspacing="3" cellpadding="3">
										<TR>
											<TD>時間</TD>
											<TD>行程</TD>
											<TD>地點</TD>
											<TD>備註</TD>
										</TR>
										<TR>
											<TD style="color:#F00">12:15~12:30</TD>
											<TD style="color:#F00">報到/從聯大出發</TD>
											<TD>聯合大學</TD>
											<TD rowspan="4">
												<P style="color:#F00">免費專車來回接駁</P>
												<P>接駁車A：</P>
												<P>聯合大學(二坪山校區活動中心)</P>
											</TD>
										</TR>
										<TR>
											<TD>12:30~14:15</TD>
											<TD>前往解密科技寶藏</TD>
											<TD>台北市三創園區</TD>
										</TR>
										<TR>
											<TD>14:15~16:15</TD>
											<TD>專人講解參觀</TD>
											<TD>台北市三創園區</TD>
										</TR>
										<TR>
											<TD>16:15~18:00</TD>
											<TD>返家</TD>
											<TD></TD>
										</TR>
									</TABLE>
									<SPAN>上車地點B：7/27(三)12:15~18:00</SPAN>
									<TABLE border="1" cellspacing="3" cellpadding="3">
										<TR>
											<TD>時間</TD>
											<TD>行程</TD>
											<TD>地點</TD>
											<TD>備註</TD>
										</TR>
										<TR>
											<TD style="color:#F00">12:15~12:30</TD>
											<TD style="color:#F00">報到/從苗創出發</TD>
											<TD>苗創中心</TD>
											<TD rowspan="4">
												<P style="color:#F00">免費專車來回接駁</P>
												<P>接駁車A：</P>
												<P>苗創中心(竹南鎮科研路36號)</P>
											</TD>
										</TR>
										<TR>
											<TD>12:30~14:15</TD>
											<TD>前往解密科技寶藏</TD>
											<TD>台北市三創園區</TD>
										</TR>
										<TR>
											<TD>14:15~16:15</TD>
											<TD>專人講解參觀</TD>
											<TD>台北市三創園區</TD>
										</TR>
										<TR>
											<TD>16:15~18:00</TD>
											<TD>返家</TD>
											<TD></TD>
										</TR>
									</TABLE>
									‬</LI>
								<LI>
									<SPAN style="color:#973520;background-color:#FF0">活動聯絡人：吳冠徵 先生/037-670050#667</SPAN>
									‬</LI>
							</UL>
							<H4 style="color:#00F;font-size:130%">注意事項：</H4>
							<P>
								<SPAN>建議參與課程資格條件：</SPAN>
								<OL style="list-style-type:decimal">
									<LI>為維持參觀品質，企業日採預約制，由<B style="color:#F00">專人進行導覽，請務必預約</B>。</LI>
									<LI>為確保您的權益，預約後未收到回覆，請來電洽詢；另，<B style="color:#F00">技術洽商受限於時間，請務必列出優先順序，如參訪當日無法進行，則由技術單位再行聯繫</B>。</LI>
									<LI>請於預約參訪時段前30分鐘報到，未準時報到或是當天無法出席者，無法保留名額及更改參訪時段。</LI>
								</OL>
							</P>
							<A style="color:#00F;font-size:160%" href="http://goo.gl/forms/YBEM3x6DmvAKbiCU2" target="_blank">2016解密科技寶藏企業日預約參訪表<SUP>←報名請點擊這裡</SUP></A>
							<P>
								<SPAN style="background-color:#808080">個資宣告</SPAN>
								<SPAN>：為提供良好服務及滿足您的權益，本展覽必須蒐集、處理所提供之個人資料。工研院已建立嚴謹資安管理制度，在不違反蒐集目的之前提下，將使用於網際網路、電子郵件、書面、傳真與其他合法方式。未來若您覺得需要調整我們提供之相關服務，您可以來電要求查詢、補充、更正或停止服務。</SPAN>
							</P>
						</DIV>
					</DIV>

					<!--足-->
					<xsl:call-template name="footer">
						<xsl:with-param name="contextPath" select="@contextPath"/>
					</xsl:call-template>
				</DIV>
			</DIV>
		</BODY>
	</xsl:template>

</xsl:stylesheet>