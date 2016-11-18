<?xml version="1.0" encoding="ISO-8859-1" standalone="no"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:output encoding="ISO-8859-1" method="html" version="4.0"/>

	<xsl:include href="classpath://com/nordicpeak/flowengine/queries/common/xsl/QueryAdminCommon.sv.xsl"/>
	<xsl:include href="RadioButtonQueryAdminTemplates.xsl"/>

	<xsl:variable name="java.queryTypeName">iVIS radiobutton query</xsl:variable>
	<xsl:variable name="java.countText">Quantity</xsl:variable>
	<xsl:variable name="java.alternativesText">Alternative</xsl:variable>
	
	<xsl:variable name="i18n.RadioButtonQueryNotFound">The required question was not found!</xsl:variable>
	
</xsl:stylesheet>
