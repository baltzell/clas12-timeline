import org.jlab.io.hipo.HipoDataSource
import org.jlab.clas.physics.Particle
import org.jlab.clas.physics.Vector3


def dstName = args[0]

def reader = new HipoDataSource()
reader.open(dstName)


// event loop
def event
def particleBank
def pionList = []
def pion
def phi
while(reader.hasEvent()) {
  event = reader.getNextEvent()
  if(event.hasBank("REC::Particle")) {
    particleBank = event.getBank("REC::Particle")
    pionList = (0..<particleBank.rows()).findAll{
      particleBank.getInt('pid',it)==211 /*&& particleBank.getShort('status',it)<0*/
    }
    println pionList
    pionList.each{ ind ->
      pion = new Particle(
        211,
        *['px','py','pz'].collect{particleBank.getFloat(it,ind)}
      )
      phi = pion.phi()
    }
  }
}

reader.close()
