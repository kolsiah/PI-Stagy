export class OffreStage {
  id: number;
  titre: string;
  description: string;
  entreprise: { id: number; nom_entreprise: string; adresse?: string; email?: string };
  type: string;
  datePublication: Date;
  debutExpiration: Date;
  etat: boolean;
  idEntreprise: number; // ID of the selected enterprise

  // Champs supplémentaires pour les détails de l'entreprise
  nomEntreprise?: string;
  adresseEntreprise?: string;

  constructor(
    id: number,
    titre: string,
    description: string,
    entreprise: { id: number; nom_entreprise: string; adresse?: string; email?: string },
    type: string,
    datePublication: Date,
    debutExpiration: Date,
    etat: boolean,
    idEntreprise: number, // ID of the selected enterprise
    nomEntreprise?: string,
    adresseEntreprise?: string
  ) {
    this.id = id;
    this.titre = titre;
    this.description = description;
    this.entreprise = entreprise;
    this.type = type;
    this.datePublication = datePublication;
    this.debutExpiration = debutExpiration;
    this.etat = etat;
    this.idEntreprise = idEntreprise;
    this.nomEntreprise = nomEntreprise;
    this.adresseEntreprise = adresseEntreprise;
  }
}
