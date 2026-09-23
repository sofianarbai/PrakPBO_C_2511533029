package pekan3;

public class Transaksi {
	String idTransaksi;
	String jenis;
	double nominal;
	
	//constructor
	public Transaksi (String id, String jenis, double nominal) {
		this.idTransaksi = id;
		this.jenis = jenis;
		this.nominal = nominal;
	}
	
	// 2. Hanya Menyediakan Getter (Read Only)
	public String getIdTransaksi () {
		return idTransaksi;
	}
	public String getJenis() {
		return jenis;
	}
	public double getNominal() {
		return nominal;
	}
	
	public void cetakDetail () {
		System.out.println("ID: " + idTransaksi + " | Jenis: " + jenis + " | Nominal :" + nominal);
	}

}
