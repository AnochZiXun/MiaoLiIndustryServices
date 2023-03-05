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
							<H1>苗栗智慧樂活網路行銷學程</H1>
							<UL>
								<LI>一、主辦單位：苗栗產業創新推動中心‬</LI>
								<LI>二、課程地點：苗創中心201會議室(苗栗縣竹南鎮科研路36號2樓)</LI>
								<LI>
									<SPAN>三、課程效益：</SPAN>
									<OL style="list-style-type:decimal">
										<LI>瞭解網路行銷基本觀念。</LI>
										<LI>運用社群行銷宣傳自家產品。</LI>
										<LI>瞭解網路行銷金、物流以及法律概念。</LI>
										<LI>實際運用現有平台上架產品行銷，開拓新客源。</LI>
									</OL>
								</LI>
								<LI>
									<SPAN>四、課程特色：</SPAN>
									<P>除講解網路行銷之金物流與法律概念外，本課程將以苗創現有之行銷網作為學員練習平台，配合課程教導的社群行銷方法做實際操作，另將導入現有平台做實作，協助學員拓展新的銷售管道。</P>
								</LI>
								<LI>
									<SPAN>五、課程時間與概要：</SPAN>
									<TABLE>
										<TR>
											<TD style="border:1px solid #000;color:#000;background-color:#FF0"></TD>
											<TD style="border:1px solid #000;color:#000;background-color:#FF0">課程一</TD>
											<TD style="border:1px solid #000;color:#000;background-color:#FF0">課程二</TD>
											<TD style="border:1px solid #000;color:#000;background-color:#FF0">課程三</TD>
											<TD style="border:1px solid #000;color:#000;background-color:#FF0">課程四</TD>
										</TR>
										<TR>
											<TD style="border:1px solid #000;color:#000;background-color:#FF0">名稱</TD>
											<TD style="border:1px solid #000">商品上架實務操作</TD>
											<TD style="border:1px solid #000">社群行銷講解</TD>
											<TD style="border:1px solid #000">金物流及法律</TD>
											<TD style="border:1px solid #000">線上出貨及客服</TD>
										</TR>
										<TR>
											<TD style="border:1px solid #000;color:#000;background-color:#FF0">時間</TD>
											<TD style="border:1px solid #000">7月7日(四)09:00~12:00</TD>
											<TD style="border:1px solid #000">7月14日(四)09:00~12:00</TD>
											<TD style="border:1px solid #000">7月21日(四)09:00~12:00</TD>
											<TD style="border:1px solid #000">7月28日(四)09:00~12:00</TD>
										</TR>
										<TR>
											<TD style="border:1px solid #000;color:#000;background-color:#FF0">大綱</TD>
											<TD style="border:1px solid #000">1.商品圖片、文字編修<BR/>2.行銷網產品上架</TD>
											<TD style="border:1px solid #000">1.社群網站經營<BR/>2.社群行銷講析<BR/>3.點擊率和廣告</TD>
											<TD style="border:1px solid #000">1.行銷金、物流介紹<BR/>2.線上交易法律講析</TD>
											<TD style="border:1px solid #000">1.進出貨管理<BR/>2.出貨、退換貨<BR/>3.客服服務</TD>
										</TR>
										<TR>
											<TD style="border:1px solid #000;color:#000;background-color:#FF0">講師</TD>
											<TD style="border:1px solid #000">優家網<BR/>張維駿副總經理<BR/>亞諾資訊<BR/>何偉寬經理</TD>
											<TD style="border:1px solid #000">合潤國際實業<BR/>鍾良萍總經理</TD>
											<TD style="border:1px solid #000">歐付寶電子支付<BR/>蔡語庭襄理</TD>
											<TD style="border:1px solid #000">工研院服科中心<BR/>羅國書副組長</TD>
										</TR>
									</TABLE>
								</LI>
								<LI>
									<SPAN>六、報名方式：</SPAN>
									<P>苗創網站(http://mii.itri.org.tw/)最新消息，有相關報名資訊及方法，請詳閱。</P>
									<UL style="list-style-type:disc">
										<LI>於網站線上報名。</LI>
										<LI>於網站「下載專區」下載105苗栗智慧樂活網路行銷學程報名表，傳真或email到苗創中心。<BR/>傳真:037-670092<BR/>Email:mii@itri.org.tw</LI>
									</UL>
								</LI>
								<LI>
									<SPAN>七、相關注意事項：</SPAN>
									<UL style="list-style-type:disc">
										<LI>課程開始的前一個星期，將有專人確認報名資格，請詳細確認報名資訊。</LI>
										<LI>若遇不可抗之因素，苗創中心保有調整上課日期及講師變動之權利。</LI>
										<LI>主辦單位保留活動變更之權利，詳細活動資訊以本中心網站公告為主。</LI>
										<LI>活動期間如遇颱風，且當地縣市政府公告停止上班，則延期辦理。</LI>
										<LI>任何問題，請洽037-670050#667苗創吳先生。</LI>
									</UL>
								</LI>
							</UL>
							<H1>報名資料</H1>
							<FORM action="{form/@action}" method="POST">
								<TABLE width="100%" border="1" cellspacing="6" cellpadding="6">
									<CAPTION>
										<xsl:value-of select="form/@errorMessage"/>
									</CAPTION>
									<TR>
										<TH class="must">
											<LABEL for="organization">公司名稱</LABEL>
										</TH>
										<TD>
											<INPUT id="organization" type="text" name="organization" value="{form/organization}"/>
										</TD>
										<TH>
											<LABEL for="number">統一編號</LABEL>
										</TH>
										<TD>
											<INPUT id="number" type="text" name="number" value="{form/number}"/>
										</TD>
									</TR>
									<TR>
										<TH>
											<LABEL for="phone">公司電話</LABEL>
										</TH>
										<TD>
											<INPUT id="phone" type="text" name="phone" value="{form/phone}"/>
										</TD>
										<TH>
											<LABEL for="fax">公司傳真</LABEL>
										</TH>
										<TD>
											<INPUT id="fax" type="text" name="fax" value="{form/fax}"/>
										</TD>
									</TR>
									<TR>
										<TH>
											<LABEL for="address">公司地址</LABEL>
										</TH>
										<TD>
											<INPUT id="address" type="text" name="address" value="{form/address}"/>
										</TD>
										<TH class="must">便當</TH>
										<TD>
											<LABEL>
												<INPUT type="radio" name="vegetarian" value="葷" checked=""/>
												<SPAN>葷</SPAN>
											</LABEL>
											<LABEL>
												<INPUT type="radio" name="vegetarian" value="素"/>
												<SPAN>素</SPAN>
											</LABEL>
											<LABEL>
												<INPUT type="radio" name="vegetarian" value="不用"/>
												<SPAN>不用</SPAN>
											</LABEL>
										</TD>
									</TR>
									<TR>
										<TH class="must">
											<LABEL for="fullname">姓名</LABEL>
										</TH>
										<TD>
											<INPUT id="fullname" type="text" name="fullname" value="{form/fullname}"/>
										</TD>
										<TH class="must">
											<LABEL for="title">部門&#47;職稱</LABEL>
										</TH>
										<TD>
											<INPUT id="title" type="text" name="title" value="{form/title}"/>
										</TD>
									</TR>
									<TR>
										<TH class="must">
											<LABEL for="cellular">行動電話</LABEL>
										</TH>
										<TD>
											<INPUT id="cellular" type="text" name="cellular" value="{form/cellular}"/>
										</TD>
										<TH class="must">
											<LABEL for="email">Email</LABEL>
										</TH>
										<TD>
											<INPUT id="email" type="text" name="email" value="{form/email}"/>
										</TD>
									</TR>
									<TR>
										<TD colspan="10">
											<INPUT style="padding:3px 6px;border:0;float:left;color:#FFF;background-color:#D17F15" type="reset" value="取消重填"/>
											<INPUT style="padding:3px 6px;border:0;float:right;color:#FFF;background-color:#FF2D21" type="submit" value="確認送出"/>
										</TD>
									</TR>
								</TABLE>
								<P>由於場地限制，每間廠商僅限定一至二人報名。</P>
							</FORM>
							<P>
								<SPAN>建議參與課程資格條件：</SPAN>
								<OL style="list-style-type:decimal">
									<LI>苗栗工商登記包含公司登記、商業登記或工廠登記等任一項。</LI>
									<LI>已有登錄合格販售之商品。</LI>
									<LI>已有自己網站及維護人員。</LI>
									<LI>具備網路行銷、雲端交易、第三方支付等系統設備。</LI>
									<LI>以上廠商申請經課程委員會審核通過。</LI>
								</OL>
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