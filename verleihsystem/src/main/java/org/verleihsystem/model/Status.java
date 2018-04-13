package org.verleihsystem.model;
//achtung! neuanordnung oder erweiterung der enum macht alte daten unbrauchbar - falls das in betracht kommt, sieh zB https://dzone.com/articles/mapping-enums-done-right 
public enum Status {

	offen,

	bestaetigt,

	abgelehnt,

	storniert,

	gebucht;

}
