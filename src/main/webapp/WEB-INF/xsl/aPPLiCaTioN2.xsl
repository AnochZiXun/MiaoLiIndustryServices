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
			<STYLE type="text/css">.aPPLiCaTioN OL,.aPPLiCaTioN UL{margin:16px 0;padding-left:16px;list-style:inside}.aPPLiCaTioN B{font-weight:bold}.aPPLiCaTioN U{text-decoration:underline}.aPPLiCaTioN CAPTION{color:#C00;text-align:center}.aPPLiCaTioN .must:before{content:'*';color:#AB4025}.aPPLiCaTioN TH:after{content:'：'}.aPPLiCaTioN TH{text-align:right}</STYLE>
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
							<!--<IMG alt="" src="{@contextPath}/IMG/application.png"/>-->
							<H1 style="font-size:160%">苗栗產業創新推動中心年度成果分享會邀請函</H1>
							<P style="margin:16px 0">親愛的貴賓您好：</P>
							<P style="margin:16px 0">苗創中心自本(104)年3月18日成立，積極推動苗栗地區產業創新與輔導，成效明顯卓著頗受業者及各界長官肯定，尤其促進產業技術提升經營轉型，結合工業技術研究院、農科院、聯合大學、亞太創意技術學院和育達科大等在地法人及大學能量，進行產業關懷輔導，今年度微型輔導已輔導超過30家廠商及形塑重點聚落3案，本活動將配合成果及媒體廣宣舉辦聯合發表活動，期創造及提升地方業者知名度，共同協助苗栗產業永續發展。</P>
							<P style="margin:16px 0">謹訂於104年11月27日(週五)上午09:30，舉辦104年度苗栗產業創新推動成果分享會，敬請撥冗參與，謝謝。</P>
							<P style="margin:16px 0;text-align:right">苗栗產業創新推動中心 敬邀</P>
							<HR/>
							<P style="margin:16px 0;text-align:center">
								<TABLE style="border:1px solid #000;border-collapse:collapse;border-spacing:6px" width="100%" cellpadding="6">
									<CAPTION>
										<H1>活動流程表</H1>
										<DIV>時間：104年11月27日（週五）上午09:30～下午14:30</DIV>
										<DIV>地點：苗栗產業創新推動中心 (苗栗縣竹南鎮科研路36號)</DIV>
									</CAPTION>
									<THEAD>
										<TR>
											<TD style="background-color:#9CF;text-align:center">時間</TD>
											<TD style="background-color:#9CF;text-align:center">議程</TD>
										</TR>
									</THEAD>
									<TBODY>
										<TR>
											<TD style="border-top:1px solid #000;text-align:center">09:30~09:45</TD>
											<TD style="border-top:1px solid #000;border-left:1px solid #000;text-align:center">報到</TD>
										</TR>
										<TR>
											<TD style="border-top:1px solid #000;text-align:center">09:45~10:00</TD>
											<TD style="border-top:1px solid #000;border-left:1px solid #000;text-align:center">熱場表演</TD>
										</TR>
										<TR>
											<TD style="border-top:1px solid #000;text-align:center">10:00~10:05</TD>
											<TD style="border-top:1px solid #000;border-left:1px solid #000;text-align:center">主席致詞</TD>
										</TR>
										<TR>
											<TD style="border-top:1px solid #000;text-align:center">10:05~10:20</TD>
											<TD style="border-top:1px solid #000;border-left:1px solid #000;text-align:center">貴賓致詞</TD>
										</TR>
										<TR>
											<TD style="border-top:1px solid #000;text-align:center">10:20~10:25</TD>
											<TD style="border-top:1px solid #000;border-left:1px solid #000;text-align:center">焦點儀式暨大合照</TD>
										</TR>
										<TR>
											<TD style="border-top:1px solid #000;text-align:center">10:25~10:40</TD>
											<TD style="border-top:1px solid #000;border-left:1px solid #000;text-align:center">參觀展示</TD>
										</TR>
										<TR>
											<TD style="border-top:1px solid #000;text-align:center">10:40~10:50</TD>
											<TD style="border-top:1px solid #000;border-left:1px solid #000;text-align:center">茶敘交流</TD>
										</TR>
										<TR>
											<TD style="border-top:1px solid #000;text-align:center">10:50~14:30</TD>
											<TD style="border-top:1px solid #000;border-left:1px solid #000;text-align:center">苗創輔導成果分享會</TD>
										</TR>
										<TR>
											<TD style="border-top:1px solid #000;text-align:center">14:30</TD>
											<TD style="border-top:1px solid #000;border-left:1px solid #000;text-align:center">散會</TD>
										</TR>
									</TBODY>
								</TABLE>
							</P>
							<P style="margin:16px 0">
								<DIV>
									<SPAN style="text-decoration:underline">指導單位</SPAN>
									<SPAN>：經濟部技術處。</SPAN>
								</DIV>
								<DIV>
									<SPAN style="text-decoration:underline">主辦單位</SPAN>
									<SPAN>：苗栗產業創新推動中心。</SPAN>
								</DIV>
								<DIV>
									<SPAN style="text-decoration:underline">執行單位</SPAN>
									<SPAN>：工業技術研究院 產服中心、國立聯合大學、亞太創意技術學院、育達科技大學。</SPAN>
								</DIV>
								<DIV>
									<SPAN style="text-decoration:underline">協辦單位</SPAN>
									<SPAN>：粉末冶金暨精微金屬製品創新產業聯盟、高值化陶瓷生活創意產業聯盟、苗栗智慧服務創新聯盟。</SPAN>
								</DIV>
							</P>
							<H6 style="margin:16px 0;font-size:110%">報名回函：</H6>
							<FORM action="{form/@action}" method="POST">
								<TABLE width="100%" border="1" cellspacing="6" cellpadding="6">
									<CAPTION>
										<xsl:value-of select="form/@errorMessage"/>
									</CAPTION>
									<TR>
										<TH class="must">
											<LABEL for="name">公司名稱</LABEL>
										</TH>
										<TD>
											<INPUT id="name" type="text" name="name" value="{form/name}"/>
										</TD>
										<TH>
											<LABEL for="address">公司地址</LABEL>
										</TH>
										<TD>
											<INPUT id="address" type="text" name="address" value="{form/address}"/>
										</TD>
									</TR>
									<TR>
										<TH class="must">
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
											<LABEL for="unifiedBusinessNumber">統一編號</LABEL>
										</TH>
										<TD>
											<INPUT id="unifiedBusinessNumber" type="text" name="unifiedBusinessNumber" value="{form/unifiedBusinessNumber}"/>
										</TD>
										<TH class="must">
											<LABEL for="fullname">姓名</LABEL>
										</TH>
										<TD>
											<INPUT id="fullname" type="text" name="fullname" value="{form/fullname}"/>
										</TD>
									</TR>
									<TR>
										<TH>
											<LABEL for="department">部門</LABEL>
										</TH>
										<TD>
											<INPUT id="department" type="text" name="department" value="{form/department}"/>
										</TD>
										<TH>
											<LABEL for="title">職稱</LABEL>
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
										<TH class="must">便當</TH>
										<TD colspan="3">
											<LABEL>
												<INPUT type="radio" name="vegetarian" value="false">
													<xsl:if test="form/vegetarian='false'">
														<xsl:attribute name="checked"/>
													</xsl:if>
												</INPUT>
												<SPAN>葷</SPAN>
											</LABEL>
											<LABEL>
												<INPUT type="radio" name="vegetarian" value="true">
													<xsl:if test="form/vegetarian='true'">
														<xsl:attribute name="checked"/>
													</xsl:if>
												</INPUT>
												<SPAN>素</SPAN>
											</LABEL>
											<LABEL>
												<INPUT type="radio" name="vegetarian" value="null">
													<xsl:if test="not(form/vegetarian='false')or not(form/vegetarian='true')">
														<xsl:attribute name="checked"/>
													</xsl:if>
												</INPUT>
												<SPAN>不用</SPAN>
											</LABEL>
										</TD>
									</TR>
									<TR>
										<TD class="c3" colspan="2">
										</TD>
									</TR>
								</TABLE>
								<INPUT style="padding:3px 6px;border:0;float:right;color:#FFF;background-color:#FF2D21" type="submit" value="確認送出"/>
								<INPUT style="padding:3px 6px;border:0;float:right;color:#FFF;background-color:#D17F15" type="reset" value="取消重填"/>
								<BR style="float:none;clear:both" clear="all"/>
							</FORM>
							<OL>
								<LI>
									<SPAN>報名方式：</SPAN>
									<OL>
										<LI>請於11/20(五)前下班前，將報名表傳真至苗創中心。</LI>
										<LI>至苗創服務網(<A href="http://mii.itri.org.tw/application.asp">http://mii.itri.org.tw/application.asp</A>)線上報名。</LI>
									</OL>
								</LI>
								<LI>傳真專線：037-670092</LI>
								<LI>聯絡電話：吳先生 037-670050轉667/<A href="mailto:itri532659@itri.org.tw">itri532659@itri.org.tw</A></LI>
							</OL>
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