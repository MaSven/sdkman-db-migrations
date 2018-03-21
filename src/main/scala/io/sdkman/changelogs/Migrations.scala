package io.sdkman.changelogs

import com.github.mongobee.changeset.{ ChangeLog, ChangeSet }
import com.mongodb.client.MongoDatabase
import org.bson.Document

import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer

@ChangeLog(order = "001")
class VersionsMigration {

  @ChangeSet(order = "001", id = "001-add_java_904_zulu", author = "marc0der")
  def migrate001(db: MongoDatabase) = db.getCollection("versions").insertMany(List[Document](
    CandidateVersion("java", "9.0.4-zulu", Some("MAC_OSX"), "https://cdn.azul.com/zulu/bin/zulu9.0.4.1-jdk9.0.4-macosx_x64.zip"),
    CandidateVersion("java", "9.0.4-zulu", Some("WINDOWS_64"), "https://cdn.azul.com/zulu/bin/zulu9.0.4.1-jdk9.0.4-win_x64.zip")).asJava)

  @ChangeSet(order = "002", id = "002-add_java_904_zulu", author = "marc0der")
  def migrate002(db: MongoDatabase) = db.getCollection("versions").insertOne(
    CandidateVersion("java", "9.0.4-zulu", Some("LINUX_64"), "https://cdn.azul.com/zulu/bin/zulu9.0.4.1-jdk9.0.4-linux_x64.tar.gz"))

  @ChangeSet(order = "003", id = "003-add_openjdk_latest", author = "MaSven")
  def migrate003(implicit db: MongoDatabase) = insertVersions(
    CandidateVersion("java", "9u181-openjdk", Some("LINUX_64"), "https://github.com/AdoptOpenJDK/openjdk9-releases/releases/download/jdk-9%2B181/OpenJDK9_x64_Linux_jdk-9.181.tar.gz"),
    CandidateVersion("java", "9u181-openjdk", Some("MAC_OSX"), "https://github.com/AdoptOpenJDK/openjdk9-releases/releases/download/jdk-9%2B181/OpenJDK9_x64_Mac_jdk-9.181.tar.gz"),
    CandidateVersion("java", "9u181-openjdk", Some("WINDOWS_64"), "https://github.com/AdoptOpenJDK/openjdk9-releases/releases/download/jdk-9%2B181/OpenJDK9_x64_Win_jdk-9.181.zip"),
    CandidateVersion("java", "8u144-openjdk", Some("LINUX_64"), "https://github.com/AdoptOpenJDK/openjdk8-releases/releases/download/jdk8u144-b01/OpenJDK8_x64_Linux_jdk8u144-b01.tar.gz"),
    CandidateVersion("java", "8u144-openjdk", Some("MAC_OSX"), "https://github.com/AdoptOpenJDK/openjdk8-releases/releases/download/jdk8u144-b01/OpenJDK8_x64_Mac_jdk8u144-b01.tar.gz"),
    CandidateVersion("java", "8u144-openjdk", Some("WINDOWS_64"), "https://github.com/AdoptOpenJDK/openjdk8-releases/releases/download/jdk8u144-b01/OpenJDK8_x64_Win_jdk8u144-b01.zip"),
    CandidateVersion("java", "10u23-openjdk", Some("LINUX_64"), "https://github.com/AdoptOpenJDK/openjdk10-releases/releases/download/jdk-10%2B23/OpenJDK10_x64_Linux_jdk-10.23.tar.gz"),
    CandidateVersion("java", "10u23-openjdk", Some("MAC_OSX"), "https://github.com/AdoptOpenJDK/openjdk10-releases/releases/download/jdk-10%2B23/OpenJDK10_x64_Mac_jdk-10.23.tar.gz"))

  @ChangeSet(order = "004", id = "004-change_java_default", author = "MaSven")
  def migrate004(implicit db: MongoDatabase) = updateCandidateDefault("java", "8u144-openjdk")

  @ChangeSet(order = "005", id = "005-change_java_default_8u152_zulu", author = "MaSven")
  def migrate005(implicit db: MongoDatabase) = updateCandidateDefault("java", "8u152-zulu")

  @ChangeSet(order = "006", id = "003-add_maven_353", author = "hho")
  def migrate006(implicit db: MongoDatabase) = {
    insertVersions(CandidateVersion("maven", "3.5.3", Some("UNIVERSAL"), "https://archive.apache.org/dist/maven/maven-3/3.5.3/binaries/apache-maven-3.5.3-bin.zip"))
    updateCandidateDefault("maven", "3.5.3")
  }

  @ChangeSet(order = "007", id = "007-change_openjdk_to_adoptopnjdk", author = "MaSven")
  def migrate007(implicit db: MongoDatabase) = {
    updateVersion("9u181-openjdk", "9u181-adoptopenjdk")
    updateVersion("8u144-openjdk", "8u144-adoptopenjdk")
    updateVersion("10u23-openjdk", "10u23-adoptopenjdk")
  }
}
