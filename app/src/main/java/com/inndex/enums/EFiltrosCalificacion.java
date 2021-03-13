package com.inndex.enums;

public enum EFiltrosCalificacion {

	TODAS(1L),
	MAYOR_A_UNO(2L),
	MAYOR_A_DOS(3L),
	MAYOR_A_TRES(4L),
	MAYOR_A_CUATRO(5L);

	private Long id;

	public static EFiltrosCalificacion getEFiltrosById(Long id) {
		for (EFiltrosCalificacion e : EFiltrosCalificacion.values()) {
			if (e.getId().equals(id)) {
				return e;
			}
		}
		return null;
	}

	private EFiltrosCalificacion(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
