
package example.com.utils

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bson.codecs.kotlinx.ObjectIdSerializer
import org.bson.types.ObjectId

object ObjectIdAsStringSerializer: KSerializer<ObjectId> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("ObjectId",PrimitiveKind.STRING)

    @OptIn(ExperimentalSerializationApi::class)
    override fun deserialize(decoder: Decoder): ObjectId =decoder.decodeSerializableValue(ObjectIdSerializer)
    override fun serialize(encoder: Encoder, value: ObjectId) = encoder.encodeString(value.toString())
}
