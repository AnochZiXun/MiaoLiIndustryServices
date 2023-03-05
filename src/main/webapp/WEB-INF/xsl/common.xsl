<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:output
		method="html"
		encoding="UTF-8"
		omit-xml-declaration="yes"
		indent="no"
		media-type="text/html"
	/>

	<!--手風琴(側邊選單)-->
	<xsl:template match="aside">
		<xsl:param name="contextPath"/>
		<ASIDE id="accordion">
			<xsl:apply-templates select="expanded">
				<xsl:with-param name="contextPath" select="$contextPath"/>
			</xsl:apply-templates>
			<xsl:apply-templates select="collapsed">
				<xsl:with-param name="contextPath" select="$contextPath"/>
			</xsl:apply-templates>
		</ASIDE>
		<IMG src="/IMG/135x47.png" alt="logo"/>
	</xsl:template>

	<!--側邊選單裡的連結-->
	<xsl:template match="collapsed|expanded">
		<xsl:param name="contextPath"/>
		<H3>
			<xsl:value-of select="@name"/>
		</H3>
		<DIV>
			<UL>
				<xsl:for-each select="anchor">
					<xsl:sort select="@ordinal" data-type="number"/>
					<LI>
						<A>
							<xsl:if test="@href">
								<xsl:attribute name="href">
									<xsl:value-of select="concat($contextPath,@href)"/>
								</xsl:attribute>
							</xsl:if>
							<xsl:value-of select="."/>
						</A>
					</LI>
				</xsl:for-each>
			</UL>
		</DIV>
	</xsl:template>

	<!--工具列-->
	<xsl:template name="toolbar">
		<xsl:param name="contextPath"/>
		<DIV id="header" class="cF">
			<DIV class="fL">
				<A href="{$contextPath}/">首頁</A>
				<xsl:if test="@title and string-length(@title)&gt;'0'">
					<SPAN>&#187;</SPAN>
					<A>
						<xsl:value-of select="@title" disable-output-escaping="yes"/>
					</A>
				</xsl:if>
			</DIV>
			<DIV class="fR">
				<A>
					<xsl:value-of select="@fullname"/>
				</A>
				<SPAN>&#187;</SPAN>
				<A class="ajaxDelete" href="{$contextPath}/">登出</A>
			</DIV>
		</DIV>
	</xsl:template>

	<!--下拉式選單群組-->
	<xsl:template match="optgroup">
		<OPTGROUP label="{@label}">
			<xsl:apply-templates/>
		</OPTGROUP>
	</xsl:template>

	<!--下拉式選單選項-->
	<xsl:template match="option">
		<OPTION value="{@value}">
			<xsl:if test="@selected">
				<xsl:attribute name="selected"/>
			</xsl:if>
			<xsl:value-of select="." disable-output-escaping="yes"/>
		</OPTION>
	</xsl:template>

	<!--分頁選擇器-->
	<xsl:template match="search">
		<xsl:if test="@previous">
			<xsl:if test="@first">
				<A class="button fontAwesome paginate" title="第一頁" tabindex="{@first}">&#xF049;</A>
			</xsl:if>
			<A class="button fontAwesome paginate" title="上一頁" tabindex="{@previous}">&#xF04A;</A>
		</xsl:if>
		<SPAN style="margin:0 3px">
			<LABEL>
				<SPAN>每頁</SPAN>
				<INPUT class="numeric" maxlength="2" name="s" type="text" value="{@size}"/>
				<SPAN>筆</SPAN>
			</LABEL>
			<SPAN>：</SPAN>
			<LABEL>
				<SPAN>第</SPAN>
				<SELECT name="p">
					<xsl:apply-templates select="*"/>
				</SELECT>
				<SPAN>&#47;</SPAN>
				<SPAN>
					<xsl:value-of select="@totalPages"/>
				</SPAN>
				<SPAN>頁</SPAN>
			</LABEL>
			<SPAN>&#40;共</SPAN>
			<SPAN>
				<xsl:value-of select="@totalElements"/>
			</SPAN>
			<SPAN>筆&#41;</SPAN>
		</SPAN>
		<xsl:if test="@next">
			<A class="button fontAwesome paginate" title="下一頁" tabindex="{@next}">&#xF04E;</A>
			<xsl:if test="@last">
				<A class="button fontAwesome paginate" title="最後頁" tabindex="{@last}">&#xF050;</A>
			</xsl:if>
		</xsl:if>
		<!--
		<INPUT id="orderBy" name="o" type="hidden" value="{@orderBy}"/>
		<INPUT id="direction" name="d" type="hidden" value="{@direction}"/>
		-->
	</xsl:template>

</xsl:stylesheet>