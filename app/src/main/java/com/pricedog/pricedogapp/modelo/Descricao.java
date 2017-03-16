package com.pricedog.pricedogapp.modelo;

import android.os.Parcel;
import android.os.Parcelable;

public class Descricao implements Parcelable{
	private long id;
	private String tipo;
	private String embalagem;
	private String marca;
	private String linha;
	private float volume;
	private String unidade;

	public Descricao()  {
	}

	protected Descricao(Parcel in) {
		tipo = in.readString();
	}

	public static final Creator<Descricao> CREATOR = new Creator<Descricao>() {
		@Override
		public Descricao createFromParcel(Parcel in) {
			return new Descricao(in);
		}

		@Override
		public Descricao[] newArray(int size) {
			return new Descricao[size];
		}
	};

	public long getId() {
		return id;
	}
	
	public void setId(long id)  {
		this.id = id;
	}
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getEmbalagem() {
		return embalagem;
	}
	public void setEmbalagem(String embalagem) {
		this.embalagem = embalagem;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getLinha() {
		return linha;
	}
	public void setLinha(String linha) {
		this.linha = linha;
	}
	public float getVolume() {
		return volume;
	}
	public void setVolume(float volume) {
		this.volume = volume;
	}
	public String getUnidade() {
		return unidade;
	}
	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(tipo);
	}

	public String toString()  {
		String string = new String();

		if (tipo != null)
			string += " " + tipo;
		if (embalagem != null)
			string += " " + embalagem;
		if (marca != null)
			string += " " + embalagem;
		if (linha != null)
			string += " " + marca;
		if (volume > 0)
			string += " " + volume;
		if (unidade != null)
			string += " " + unidade;

		return string;
	}
}