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
			<STYLE type="text/css">.aPPLiCaTioN OL,.aPPLiCaTioN UL{margin:16px 0;padding-left:16px;list-style-position:inside}.aPPLiCaTioN B{font-weight:bold}.aPPLiCaTioN U{text-decoration:underline}.aPPLiCaTioN CAPTION{color:#C00;text-align:center}.aPPLiCaTioN .must:before{content:'*';color:#AB4025}.aPPLiCaTioN TH:after{content:'：'}.aPPLiCaTioN TH{text-align:right}</STYLE>
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
							<h1 style="text-align: center;">
								<span style="font-size:36px">
									<strong>研發創新計畫書撰寫技巧與實務應用 實戰班</strong>
								</span>
							</h1>
							<h2>
								<span style="color:#0000FF">
									<strong>課程簡介</strong>
								</span>
							</h2>
							<p>為鼓勵企業強化研發投入，政府每年編列大筆研發經費補助企業從事技術開發工作，期望協助企業可運用政府研發補助資源，降低自身研發風險。故舉辦本次課程，輔導積極提升研發能力或有意願申請政府研發補助資源的企業，授予申請重點和技巧，強化研發補助案通過的能力，進而優化企業研發實力，增加全球競爭優勢。本課程以講師多年在政府機構辦理研發補助計畫的經驗，教導計畫書撰寫、執行計畫研發的訓練，期望協助企業提升通過政府地方型輔導審查機制的機會，獲取政府資源補助，強化企業研發競爭優勢。</p>
							<h2>
								<strong>
									<span style="color:#0000FF">課程目標</span>
								</strong>
							</h2>
							<ol>
								<li>1.使學員習得計畫書撰寫，培養政府計畫書撰寫技巧與實務能力養成</li>
							</ol>
							<h2>
								<span style="color:#0000FF">
									<strong>課程特色</strong>
								</span>
							</h2>
							<ol>
								<li>1.習得申請政府計畫資源知識</li>
								<li>2.參加全程課程者，授予「課程結業證書」</li>
								<li>3.完訓者之公司，可優先申請苗栗產業創新推動中心輔導資源資格</li>
							</ol>
							<h2>
								<span style="color:#0000FF">
									<strong>開課資訊</strong>
								</span>
							</h2>
							<ul>
								<li>●日期：<ul>
										<li>105年3月31日 09：00～16：00；共計6小時</li>
										<li>105年4月7~8日09：00～17：00；共計14小時</li>
									</ul>
								</li>
								<li>●地點：竹南鎮 科研路 36 號(苗栗產業創新推動中心)</li>
								<li>●費用：免費</li>
								<li>●適用產業：擬申請政府創新研發輔導之苗栗地方傳統產業為優先，包括：粉末冶金產業、陶瓷產業、休閒觀光產業&#8230;&#8230;等相關產業</li>
								<li>●研修對象限：公司主管、人員</li>
								<li>●培訓人數：40-50人</li>
								<li>●授課方式：演講、討論、案例演練</li>
								<li>●核發課程結業證書資格：凡參加本研習營，出席率達80%者，將核發工研院產業學院「課程結業證書」憑證。</li>
							</ul>
							<h2>
								<span style="color:#0000FF">
									<strong>課程內容</strong>
								</span>
							</h2>
							<table border="1" cellpadding="6" cellspacing="6">
								<tbody>
									<tr>
										<td style="text-align: center;">日期</td>
										<td style="text-align: center;">內容大綱</td>
										<td style="text-align: center;">時數</td>
										<td style="text-align: center;">講師</td>
									</tr>
									<tr>
										<td style="text-align: center;">105/3/31</td>
										<td>
											<ol>
												<li>一、創新/研發內涵概念與認知</li>
												<li>二、計畫申請應有的準備與提案評估<ol>
														<li>1.計畫申請應有的準備</li>
														<li>2.計畫申請-提案評估</li>
														<li>3.計畫申請書須提供的資訊</li>
													</ol>
												</li>
												<li>三、SBIR計畫書之整體架構剖析</li>
												<li>四、公司概況-如何表達公司執行科技專案能力</li>
												<li>五、研究題目擬定<ol>
														<li>1.政府計畫書之創新與研發(R&amp;D)內涵</li>
														<li>2.如何破題與選題</li>
													</ol>
												</li>
												<li>七、產業及市場研究資料蒐集技巧與專利檢索分析</li>
												<li>八、計畫緣起動機<ol>
														<li>1.產業背景說明</li>
														<li>2.問題分析與確認</li>
													</ol>
												</li>
												<li>九、SWOT及市場競爭力分析</li>
												<li>十、創新性擬定<ol>
														<li>1.運用 Google、論文資料庫&#8230;尋找技術及專利資料</li>
														<li>2.如何運用實體圖書館及產業研究機構資源</li>
													</ol>
												</li>
												<li>十一、計畫定位與目標設定</li>
												<li>十二、研發工作分配</li>
												<li>十三、進度及查核點設計</li>
												<li>十四、風險評估與智財分析</li>
												<li>十五、預期效益展現與計畫經費編列<ol>
														<li>1.設定計畫架構、分項計畫及執行方案</li>
														<li>2.設定 KPI及查核點</li>
														<li>3.設定預期效益及產業關聯效益</li>
														<li>4.經費會計編列原則探討</li>
													</ol>
												</li>
												<li>十六、個案討論/交流時間</li>
											</ol>
										</td>
										<td style="text-align: center;">6</td>
										<td style="text-align: center;">莊天旺</td>
									</tr>
									<tr>
										<td style="text-align: center;">105/4/7~8</td>
										<td>
											<ol>
												<li>一、計畫書及提案簡報修改<ol>
														<li>1.針對提案廠商計畫書、提案簡報輔導修正(含報告以及口頭、紙本資料回饋方式進行)</li>
													</ol>
												</li>
											</ol>
										</td>
										<td style="text-align: center;">14</td>
										<td style="text-align: center;">莊天旺</td>
									</tr>
								</tbody>
							</table>
							<p>備註：主辦單位保有課程內容修改權利。</p>
							<h2>
								<span style="color:#0000FF">
									<strong>講師介紹</strong>
								</span>
							</h2>
							<p>莊天旺 先生</p>
							<p>經歷：(2009年工研院產業學院 桂冠講師)</p>
							<ul>
								<li>- 工研院產業學院政府研發補助計畫計畫書撰寫、審查與計畫管理實務課程 講師</li>
								<li>- 工研院、紡織所、國家實驗研究院、精密機械中心、塑膠中心、景文科技大學&#8230;等政府研發補助計畫內訓課程 講師</li>
								<li>- 經濟部委任公務人員晉升薦任官訓練班 講師</li>
								<li>- 科技部中科、南科101~104年研發精進產學合作計畫計畫申請書撰寫技巧與輔導課程 講師</li>
								<li>- 科技部竹科103年MG+4C垂直整合推動專案計畫計畫書撰寫技巧與輔導課程 講師</li>
								<li>- 科技部南科104年南部綠能低碳產業聚落發展計畫計畫書撰寫技巧與輔導課程 講師</li>
							</ul>
							<BR/>
							<FORM action="{form/@action}" method="POST">
								<TABLE width="100%" border="1" cellspacing="6" cellpadding="6">
									<CAPTION>
										<xsl:value-of select="form/@errorMessage"/>
									</CAPTION>
									<TR>
										<TH>
											<LABEL for="organization">單位名稱</LABEL>
										</TH>
										<TD colspan="3">
											<INPUT id="organization" type="text" name="organization" value="{form/organization}"/>
										</TD>
									</TR>
									<TR>
										<TH class="must">
											<LABEL for="fullname">姓名</LABEL>
										</TH>
										<TD>
											<INPUT id="fullname" type="text" name="fullname" value="{form/fullname}"/>
										</TD>
										<TH>
											<LABEL for="title">職稱</LABEL>
										</TH>
										<TD>
											<INPUT id="title" type="text" name="title" value="{form/title}"/>
										</TD>
									</TR>
									<TR>
										<TH>
											<LABEL for="phone">連絡電話</LABEL>
										</TH>
										<TD>
											<INPUT id="phone" type="text" name="phone" value="{form/phone}"/>
										</TD>
										<TH class="must">
											<LABEL for="email">電子郵件</LABEL>
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
													<xsl:if test="not(form/vegetarian='true')">
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
										</TD>
									</TR>
									<TR>
										<TD colspan="4">
											<INPUT style="padding:3px 6px;border:0;float:left;color:#FFF;background-color:#D17F15" type="reset" value="取消重填"/>
											<INPUT style="padding:3px 6px;border:0;float:right;color:#FFF;background-color:#FF2D21" type="submit" value="確認送出"/>
										</TD>
									</TR>
								</TABLE>
							</FORM>
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