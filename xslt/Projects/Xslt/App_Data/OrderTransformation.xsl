<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/">
<div class="container">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <h1 class="text-center">Szczegóły zamówienia:</h1>
            </div>
            <div class="panel-body">
                <h4>Numer:
                    <xsl:value-of select="order/orderId" />
                </h4>
                <h4>Miejsce startu:
                    <xsl:value-of select="order/startLocation" />
                </h4>
                <h4>Miejsce docelowe:
                    <xsl:value-of select="order/endLocation" />
                </h4>
                <h3>Szacowany koszt:
                    <xsl:value-of select="order/cost" /> zł
                </h3>
                <xsl:variable name="status" select="order/status" />
                <xsl:choose>
                    <xsl:when test="$status = 'inProgress'">
                        <div class="alert alert-success" role="alert">
                            <p class="text-center">Zamówienie jest realizowane</p>
                        </div>
                    </xsl:when>
                  <xsl:when test="$status = 'cancelled'">
                    <div class="alert alert-danger" role="alert">
                      <p class="text-center">Zamówienie anulowane</p>
                    </div>
                  </xsl:when>
                    <xsl:otherwise>
                        <div class="alert alert-warning" role="alert">
                            <p class="text-center">Brak informacji o statusie zamówienia - spróbuj odświeżyć stronę</p>
                        </div>
                    </xsl:otherwise>
                </xsl:choose>
            </div>
        </div>
    </div>
</xsl:template>
</xsl:stylesheet>
