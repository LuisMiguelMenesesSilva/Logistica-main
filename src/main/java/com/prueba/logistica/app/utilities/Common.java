package com.prueba.logistica.app.utilities;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Common {
	
	/**
	 * 
	 * {@Resumen - metodo que calcula el cantidad a descontar al precio de envío.}
	 * @param precioEnvio monto total de los productos enviados.
	 * @param montoDescuento valor porcentual de descuento dependiendo el tipo de logistica .
	 * 
	 * @author luis.meneses
	 * @version 1.0
	 * @since 2025-06-6
	*/
	public static BigDecimal calcularDescuentoLogisticaT(BigDecimal precioEnvio,double montoDescuento) {
		BigDecimal descuento = new BigDecimal(montoDescuento);
		return (precioEnvio.multiply(descuento)).setScale(2,RoundingMode.HALF_UP) ;
		
	}
	
	/**
	 * 
	 * {@Resumen - metodo que aplica el descuento al precio de envio y devuelve un Bigdecimal.}
	 * 
	 * @param precioEnvio monto total de los productos enviados.
	 * @param montoDescuento valor porcentual de descuento dependiendo el tipo de logistica .
	 * 
	 * @author luis.meneses
	 * @version 1.0
	 * @since 2025-06-6
	*/
	public static BigDecimal aplicarDescuento(BigDecimal precioEnvio,double montoDescuento) {
		return precioEnvio.subtract(calcularDescuentoLogisticaT(precioEnvio,montoDescuento)).setScale(2,RoundingMode.HALF_UP);
	}

}
